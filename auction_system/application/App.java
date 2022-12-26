package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

import application.gui.LoginScreen;
import application.managers.AccountManager;
import application.managers.AuctionManager;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class App extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * opens the default screen (LoginScreen) and loads all saved data using deserialization
	 */
	@Override
	public void start(Stage stage) throws Exception {
		new LoginScreen(stage);
		AccountManager.getInstance();
		AuctionManager.getInstance();
	}
}
