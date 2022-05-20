package application.controllers;

import application.exceptions.ExistingAccountException;
import application.gui.AdminScreen;
import application.gui.LoginScreen;
import application.gui.MenuScreen;
import application.gui.RegisterScreen;
import application.managers.AccountManager;
import application.users.Account;
import application.users.Admin;
import application.users.User;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class RegisterController {
	
	private RegisterScreen screen;

	public RegisterController(RegisterScreen screen) {
		this.screen = screen;
	}
	/**
	 * validates the entered information and creates a new account based on them
	 */
	public void register(Stage s, String name, String email, String pass, String reppass, boolean isAdmin) throws ExistingAccountException{
		if(name.isBlank() || email.isBlank() || pass.isBlank() || reppass.isBlank()) {
			this.screen.setLabel("Some fields were left empty");
		}
		else {
			if(!pass.equals(reppass)) {
				this.screen.setLabel("Passwords are not equal");
			}
			else {
				Account acc = null;
				boolean adminChecker = false;
				if(isAdmin) {
					for(Account a: AccountManager.getInstance().getAccounts()) {
						if(a instanceof Admin) adminChecker = true;
					}
					if(adminChecker) this.screen.setLabel("Only one admin account can exist.");
					else {
						acc = new Admin(name, email, pass);
						AccountManager.getInstance().addNewUser(acc);
						AccountManager.getInstance().setCurrentUser(acc, true);
						new AdminScreen(s);
					}
				}
				else {
					for(Account a: AccountManager.getInstance().getAccounts()) {
						if(a.getEmail().equals(email)) throw new ExistingAccountException("Email address is already in use!");
					}
					acc = new User(name, email, pass);
					AccountManager.getInstance().addNewUser(acc);
					AccountManager.getInstance().setCurrentUser(acc, false);
					new MenuScreen(s);
				}
			}
		}
		
	}
	/**
	 * opens a new LoginScreen
	 */
	public void moveToLogin(Stage stage) {
		new LoginScreen(stage);
	}
}
