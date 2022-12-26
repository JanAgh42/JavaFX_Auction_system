package application.controllers;

import application.auction.Auction;
import application.gui.NotifInfoDialog;
import application.gui.SendNotifDialog;
import application.managers.AccountManager;
import application.tools.Notification;
import application.users.Account;
import application.users.Admin;
import application.users.User;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class NotifDialogController {

	private NotifInfoDialog screen;
	private Notification<?> notification;
	
	public NotifDialogController(NotifInfoDialog screen, Notification<?> notification) {
		this.screen = screen;
		this.notification = notification;
		
		this.setLabels();
	}
	/**
	 * sets the correct labels
	 */
	public void setLabels() {
		if(this.notification.getObject() instanceof Auction) {
			this.screen.setName("Auction name: " + ((Auction) this.notification.getObject()).getName());
		}
		else if(this.notification.getObject() instanceof User) {
			this.screen.setName("User email: " + ((User) this.notification.getObject()).getEmail());
		}
	}
	/**
	 * replies to the previous message
	 */
	public void reply() {
		if(this.notification.getObject() instanceof Admin || this.notification.getObject() instanceof User) {
			new SendNotifDialog(new Stage(), (Account) this.notification.getObject());
		}
		else this.screen.setLabel("You cannot reply to this message.");
	}
	/**
	 * deletes the selected Notification object
	 */
	public void removeSeenNotif(Stage stage) {
		Account account = AccountManager.getInstance().getCurrentUser();
		account.removeNotif(this.notification);
		stage.close();
	}
}
