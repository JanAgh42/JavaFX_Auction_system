package application.controllers;

import application.gui.BanScreen;
import application.gui.SendNotifDialog;
import application.managers.AccountManager;
import application.users.Account;
import application.users.User;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class BanController {
	
	private BanScreen screen;
	private User user;
	
	public BanController(BanScreen screen, User user) {
		this.screen = screen;
		
		this.user = user;
		
		this.setLabels();
	}
	/**
	 * updates GUI according to the user's status
	 */
	private void setLabels() {
		boolean banned = false;
		
		for(Account a: AccountManager.getInstance().getBannedAccounts()) {
			if(a instanceof User && ((User) a).equals(this.user)) banned = true;
		}
		
		this.screen.setLabel(banned ? "Status:   banned" : "Status:   active");
		this.screen.setButton(banned ? "Restore user" : "Ban user");
	}
	/**
	 * performs banning or unbanning of users
	 */
	public void banOrUnbanUser() {
		boolean banned = false;
		
		for(Account a: AccountManager.getInstance().getBannedAccounts()) {
			if(a instanceof User && ((User) a).equals(this.user)) banned = true;
		}
		
		if(banned) {
			AccountManager.getInstance().getBannedAccounts().remove(this.user);
			AccountManager.getInstance().getAccounts().add(this.user);
			this.user.setAttempts();
		}
		else {
			AccountManager.getInstance().getAccounts().remove(this.user);
			AccountManager.getInstance().getBannedAccounts().add(this.user);
		}
		AccountManager.getInstance().saveState();
		this.setLabels();
	}
	/**
	 * opens a new SendNotifDialog
	 */
	public void sendNotification(User user) {
		new SendNotifDialog(new Stage(), user);
	}
}
