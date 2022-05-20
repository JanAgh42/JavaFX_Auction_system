package application.users;

import java.util.ArrayList;

import application.managers.AccountManager;
import application.payment.CryptoWallet;
import application.payment.FIATWallet;
import application.payment.Wallet;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class User extends Account{
	
	private static final long serialVersionUID = 5620216984428040561L;
	private ArrayList<Integer> ownAuctionIDs;
	private ArrayList<Integer> followedAuctionIDs;
	private ArrayList<Wallet> userWallets;
	
	private int numOfAttempts = 4;
	
	public User(String name, String email, String password) {
		super(name, email, password);
		
		this.ownAuctionIDs = new ArrayList<>();
		this.followedAuctionIDs = new ArrayList<>();
		this.userWallets = new ArrayList<>();
		
		this.userWallets.add(new FIATWallet());
	}
	/**
	 * creates a new crypto wallet for the given user
	 */
	public void createCryptoWallet(String password) {
		for(Wallet w: this.userWallets) {
			if(w instanceof CryptoWallet) return;
		}
		this.userWallets.add(new CryptoWallet(password));
		AccountManager.getInstance().saveState();
	}
	/**
	 * returns the user's regular wallet
	 */
	public FIATWallet getFiatWallet() {
		for(Wallet w: this.userWallets) {
			if(w instanceof FIATWallet) return (FIATWallet) w;
		}
		return null;
	}
	/**
	 * returns the user's crypto wallet (if he has one)
	 */
	public CryptoWallet getCryptoWallet() {
		for(Wallet w: this.userWallets) {
			if(w instanceof CryptoWallet) return (CryptoWallet) w;
		}
		return null;
	}
	/**
	 * 
	 */
	public void addFollow(int ID) {
		this.followedAuctionIDs.add(ID);
	}
	
	public ArrayList<Integer> getFollowed() {
		return this.followedAuctionIDs;
	}
	
	public ArrayList<Integer> getOwnAuctions(){
		return this.ownAuctionIDs;
	}
	/**
	 * sets the number of attempts of password entering back to 4 
	 */
	public void setAttempts() {
		this.numOfAttempts = 4;
	}
	/**
	 * decreases the number of attempts
	 */
	public void decrAttempts() {
		this.numOfAttempts--;
	}
	
	public int getAttempts() {
		return this.numOfAttempts;
	}
	/**
	 * returns some essential details about the given user
	 */
	@Override
	public String toString() {
		return this.getName() + "		" + this.getEmail() + "		Current number of auctions:	" + this.getOwnAuctions().size();
	}
}
