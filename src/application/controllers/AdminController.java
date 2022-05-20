package application.controllers;

import application.gui.LoginScreen;
import application.gui.NotificationScreen;
import application.gui.AdminScreen;
import application.gui.BanScreen;
import application.gui.SendNotifDialog;
import application.managers.AccountManager;
import application.managers.AuctionManager;
import application.tools.Multithread;
import application.users.Account;
import application.users.User;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class AdminController{
	
	private AdminScreen screen;

	public AdminController(AdminScreen screen) {
		this.screen = screen;
		this.populateListView();
	}
	/**
	 * logs out the admin, saves all changes and returns to LoginScreen
	 */
	public void logout(Stage stage) {
		new LoginScreen(stage);
		AccountManager.getInstance().setCurrentUser(null, true);
		AccountManager.getInstance().saveState();
		AuctionManager.getInstance().saveState();
	}
	/**
	 * populates listview with existing users using multithreading
	 */
	public void populateListView() {
		Multithread<AdminScreen> populate = new Multithread<>(this.screen, view -> {
			for(Account a: AccountManager.getInstance().getAccounts()) {
				if(a instanceof User) this.screen.getAccs().getItems().add((User) a);
			}
			for(Account a: AccountManager.getInstance().getBannedAccounts()) {
				if(a instanceof User) this.screen.getAccs().getItems().add((User) a);
			}
			return null;
		});
		
		populate.start();
	}
	/**
	 * opens a new NotificationScreen
	 */
	public void moveToNotifications(Stage stage){
		new NotificationScreen(stage, screen -> {
			new AdminScreen(screen);
			return null;
		});
	}
	/**
	 * open a dialog box with the selected user's info
	 */
	public void openUserDialog() {
		User user = this.screen.getAccs().getSelectionModel().getSelectedItem();
		if(user != null) new BanScreen(new Stage(), user);
	}
	/**
	 * opens a new SendNotifDialog
	 */
	public void sendGroupMessage() {
		new SendNotifDialog(new Stage(), null);
	}
}
