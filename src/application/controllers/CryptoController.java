package application.controllers;

import application.gui.AccountScreen;
import application.gui.AddFundsDialog;
import application.gui.CryptoScreen;
import application.managers.AccountManager;
import application.tools.Constants;
import application.users.User;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class CryptoController {

	private CryptoScreen screen;
	private User user;
	
	public CryptoController(CryptoScreen screen) {
		this.screen = screen;
		
		this.user = (User) AccountManager.getInstance().getCurrentUser();
		this.user.getCryptoWallet().subscribe(this.screen);
		
		this.screen.setLabel("Current crypto balance: " + this.user.getCryptoWallet().getFunds() + " ETH");
	}
	/**
	 * returns back to AccountScreen
	 */
	public void moveToAccount(Stage stage) {
		this.user.getCryptoWallet().subscribe(this.screen);
		new AccountScreen(stage);
	}
	/**
	 * opens a new AddFundsDialog
	 */
	public void openCryptoDialog() {
		new AddFundsDialog(new Stage(), Constants.CRYPTO);
	}
}
