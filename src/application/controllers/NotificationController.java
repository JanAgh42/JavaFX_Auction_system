package application.controllers;

import application.gui.NotifInfoDialog;
import application.gui.NotificationScreen;
import application.managers.AccountManager;
import application.tools.Multithread;
import application.tools.Notification;
import application.users.Account;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class NotificationController {
	
	private NotificationScreen screen;
	private Outcome<Stage> option;

	public NotificationController(NotificationScreen screen, Outcome<Stage> option) {
		this.screen = screen;
		this.option = option;
		AccountManager.getInstance().getCurrentUser().subscribe(this.screen);
		this.populateListView();
	}
	/**
	 * opens a new screen based on the entered lambda
	 */
	public void moveToAccount(Stage stage) {
		AccountManager.getInstance().getCurrentUser().unsubscribe(this.screen);
		this.option.outcome(stage);
	}
	/**
	 * populates listview with the given user's notifications and messages
	 */
	public void populateListView() {
		Multithread<NotificationScreen> populate = new Multithread<>(this.screen, view -> {
			Account account = AccountManager.getInstance().getCurrentUser();
			
			for(Notification<?> n: account.getNotifs()) {
				view.getNotifs().getItems().add(0, n);
			}
			return null;
		});
		
		populate.start();
	}
	/**
	 * empties the listview
	 */
	public void clearListView() {
		this.screen.getNotifs().getItems().clear();
	}
	/**
	 * opens a dialog box with the selected notification's info
	 */
	public void openSelectedNotification() {
		Notification<?> n = this.screen.getNotifs().getSelectionModel().getSelectedItem();
		if(n != null) new NotifInfoDialog(new Stage(), n);
	}
}
