package application.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import application.exceptions.WrongPasswordException;
import application.gui.AdminScreen;
import application.gui.LoginScreen;
import application.gui.MenuScreen;
import application.gui.RegisterScreen;
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
public class LoginController {
	
	private LoginScreen screen;
	
	public LoginController(LoginScreen screen) {
		this.screen = screen;
	}
	/**
	 * validate the entered info and logs in the user
	 */
	public void logIn(Stage stage, String email, String password) {
		Account acc = null;
		boolean banned = false;
		
		for(Account a: AccountManager.getInstance().getBannedAccounts()) {
			if(a.getEmail().equals(email)) banned = true;
		}
		
		if(!banned) {
			for(Account a: AccountManager.getInstance().getAccounts()) {
				if(a.getEmail().equals(email) && a.getPassword().equals(password)) acc = a;
				else if(a.getEmail().equals(email) && !a.getPassword().equals(password)) {
					if(a instanceof User) {
						try {
							if(((User) a).getAttempts() <= 0) {
								this.banUser((User) a); break;
							}
							else throw new WrongPasswordException("Wrong password. Attempts remaining before ban: ", (User) a);
						}catch(WrongPasswordException wpe) {
							this.screen.setLabel(wpe.getMessage());
						}
					} 
					else this.screen.setLabel("Wrong password.");
					return;
				}
			}
		}
		
		if(banned) this.screen.setLabel("Account has been banned.");
		else {
			if(acc == null) this.screen.setLabel("Account does not exist.");
			else {
				if(acc instanceof Admin) {
					AccountManager.getInstance().setCurrentUser(acc, true);
					new AdminScreen(stage);
				}
				else {
					((User) acc).setAttempts();
					AccountManager.getInstance().setCurrentUser(acc, false);
					new MenuScreen(stage);
				}
			}
		}
	}
	/**
	 * bans the given user after entering a wrong password 4 times in a row
	 */
	private void banUser(User user) {
		AccountManager.getInstance().getAccounts().remove(user);
		AccountManager.getInstance().getBannedAccounts().add(user);
		Admin admin = null;
		
		for(Account a: AccountManager.getInstance().getAccounts()) {
			if(a instanceof Admin) admin = (Admin) a;
		}
		if(admin != null) {
			String message = "Wrong password entered 4 times in a row.";
			String time = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
			
			admin.inform(new Notification<User>(message, time, user, ban -> {
				return ban.getName() + "    " + ban.getEmail();
			}));
		}
	}
	/**
	 * opens a new RegisterScreen
	 */
	public void moveToRegister(Stage stage) {
		new RegisterScreen(stage);
	}
}
