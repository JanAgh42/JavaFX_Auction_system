package application.controllers;

import application.gui.CryptoPassDialog;
import application.managers.AccountManager;
import application.users.User;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class CryptoPassController {

	private CryptoPassDialog screen;
	private Outcome<Stage> option;
	
	public CryptoPassController(CryptoPassDialog screen, Outcome<Stage> option) {
		this.screen = screen;
		this.option = option;
		
		this.hasCryptoWallet();
	}
	/**
	 * checks if the given user has a crypto wallet
	 */
	public void hasCryptoWallet() {
		User user = (User) AccountManager.getInstance().getCurrentUser();
		
		this.screen.setUpLayout(user.getCryptoWallet() != null ? true : false);
	}
	/**
	 * creates a crypto wallet if the user doesn't have one
	 */
	public void initCryptoWallet(String password, String repeat, Stage stage, Stage main) {
		if(password.isBlank() || repeat.isBlank()) this.screen.setLabel("Some fields were left empty!");
		else if(!password.equals(repeat)) this.screen.setLabel("Passwords are not equal!");
		else {
			User user = (User) AccountManager.getInstance().getCurrentUser();
			user.createCryptoWallet(password);
			
			option.outcome(main);
			stage.close();
		}
	}
	/**
	 * opens the user's crypto wallet after entering the correct password
	 */
	public void initCryptoWallet(String password, Stage stage, Stage main) {
		User user = (User) AccountManager.getInstance().getCurrentUser();
		
		if(password.isBlank()) this.screen.setLabel("Some fields were left empty!");
		else if(!password.equals(user.getCryptoWallet().getPassword())) this.screen.setLabel("Incorrect password!");
		else {
			option.outcome(main);
			stage.close();
		}
	}
}
