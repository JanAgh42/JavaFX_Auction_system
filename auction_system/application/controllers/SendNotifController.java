package application.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import application.gui.SendNotifDialog;
import application.managers.AccountManager;
import application.observer.Subject;
import application.tools.Multithread;
import application.tools.Notification;
import application.users.Account;
import application.users.Admin;
import application.users.User;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class SendNotifController extends Subject<Account>{
	
	private Account user;
	
	public SendNotifController(Account user) {
		this.user = user;
		
		if(this.user == null) this.subscribeUsers();
	}
	/**
	 * sends a message to a given user
	 */
	public void sendMessage(Stage stage, String message) {
		String time = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
		
		this.user.inform(new Notification<Account>(message, time, AccountManager.getInstance().getCurrentUser(), adm -> {
			return "from: " + adm.getName();
		}));
		AccountManager.getInstance().saveState();
		stage.close();
	}
	/**
	 * subscribes all users to the given group message using multithreading
	 */
	private void subscribeUsers() {
		Multithread<SendNotifDialog> observer = new Multithread<>(null, account -> {
			for(Account a: AccountManager.getInstance().getAccounts()) {
				if(a instanceof User) this.subscribe(a);
			}
			for(Account a: AccountManager.getInstance().getBannedAccounts()) {
				if(a instanceof User) this.subscribe(a);
			}
			return null;
		});
		observer.start();
	}
	/**
	 * unsubscribes all users after sending the group message
	 */
	private void unsubscribeUsers() {
		Multithread<SendNotifDialog> observer = new Multithread<>(null, account -> {
			for(Account a: AccountManager.getInstance().getAccounts()) {
				if(a instanceof User) this.unsubscribe(a);
			}
			for(Account a: AccountManager.getInstance().getBannedAccounts()) {
				if(a instanceof User) this.unsubscribe(a);
			}
			return null;
		});
		observer.start();
	}
	/**
	 * sends a group message from the admin
	 */
	public void sendGroupMessage(Stage stage, String message) {
		String time = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
		Admin admin = (Admin) AccountManager.getInstance().getCurrentUser();
		
		Multithread<SendNotifDialog> observer = new Multithread<>(null, account -> {
			this.update(new Notification<Admin>(message, time, admin, adm -> {
				return "from: " + adm.getName();
			}));
			return null;
		});
		
		observer.start();
		this.unsubscribeUsers();
		
		AccountManager.getInstance().saveState();
		stage.close();
	}
}
