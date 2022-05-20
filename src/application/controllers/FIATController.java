package application.controllers;

import application.gui.AccountScreen;
import application.gui.AddFundsDialog;
import application.gui.FIATScreen;
import application.managers.AccountManager;
import application.tools.Constants;
import application.users.User;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class FIATController {
	
	private FIATScreen screen;
	private User user;

	public FIATController(FIATScreen screen) {
		this.screen = screen;
		
		this.user = (User) AccountManager.getInstance().getCurrentUser();
		this.user.getFiatWallet().subscribe(this.screen);
		
		this.screen.setLabel("Current balance: " + this.user.getFiatWallet().getFunds() + "€");
	}
	/**
	 * opens a new AccountScreen
	 */
	public void moveToAccount(Stage stage) {
		user.getFiatWallet().unsubscribe(this.screen);
		new AccountScreen(stage);
	}
	/**
	 * opens a new AddFundsDialog
	 */
	public void openFIATDialog() {
		new AddFundsDialog(new Stage(), Constants.FIAT);
	}
}
