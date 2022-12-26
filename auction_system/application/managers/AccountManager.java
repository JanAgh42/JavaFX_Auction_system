package application.managers;

import java.io.IOException;
import java.util.ArrayList;

import application.users.Account;
import application.users.Admin;
import application.users.User;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class AccountManager implements Manager{
	
	private static AccountManager accounts;
	private ArrayList<Account> existingUsers;
	private ArrayList<Account> bannedUsers;
	private Account currentUser = null;
	
	private String path = "./accounts.ser";
	private StateSaver<ArrayList<Account>> saver = null;
	
	private AccountManager() {
		this.loadState();
	}
	/**
	 * Singleton class - returns the existing instance
	 */
	public static AccountManager getInstance() {
		if(accounts == null) accounts = new AccountManager();
		return accounts;
	}
	
	public ArrayList<Account> getAccounts(){
		return this.existingUsers;
	}
	
	public ArrayList<Account> getBannedAccounts(){
		return this.bannedUsers;
	}
	/**
	 * adds a new user to the list of users and serializes everything
	 */
	public void addNewUser(Account account) {
		this.existingUsers.add(account);
		this.saveState();
	}
	/**
	 * saves all users (regular and banned ones) using serialization
	 */
	public void saveState() {
		try {
			this.saver = new StateSaver<>(this.path, this.existingUsers);
			this.saver.serialize();
			
			this.saver.setObject(this.bannedUsers);
			this.saver.serialize();
			this.saver.closeWriteStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * loads all users (regular and banned ones) from the data files using deserialization
	 */
	public void loadState() {
		this.saver = new StateSaver<>(this.path, this.existingUsers);
		
		try {
			this.existingUsers = this.saver.deserialize();
		} catch (ClassNotFoundException | IOException e) {
			this.existingUsers = new ArrayList<>();
		}
		
		this.saver.setObject(this.bannedUsers);
		
		try {
			this.bannedUsers = this.saver.deserialize();
			this.saver.closeReadStream();
		} catch (ClassNotFoundException | IOException e) {
			this.bannedUsers = new ArrayList<>();
		}
	}
	
	public Account getCurrentUser() {
		return this.currentUser;
	}
	/**
	 * chooses and sets the currently logged in user
	 */
	public void setCurrentUser(Account account, boolean isAdmin) {
		if(isAdmin) this.currentUser = (Admin) account;
		else this.currentUser = (User) account;
	}
}
