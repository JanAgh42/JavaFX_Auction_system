package application.controllers;

import application.auction.Auction;
import application.gui.AccountScreen;
import application.gui.CryptoPassDialog;
import application.gui.CryptoScreen;
import application.gui.FIATScreen;
import application.gui.MenuScreen;
import application.gui.NotificationScreen;
import application.managers.AccountManager;
import application.managers.AuctionManager;
import application.tools.Multithread;
import application.users.User;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class AccountController{

	private AccountScreen screen;
	
	public AccountController(AccountScreen screen) {
		this.screen = screen;
		this.populateListView();
		
		User user = (User) AccountManager.getInstance().getCurrentUser();
		
		this.screen.setName("Name: " + user.getName());
		this.screen.setEmail("Email: " + user.getEmail());
		this.screen.setAucAmount("Own auctions: " + user.getOwnAuctions().size());
	}
	/**
	 * opens a new MenuScreen
	 */
	public void moveToMenu(Stage stage) {
		new MenuScreen(stage);
	}
	/**
	 * opens a new FIATScreen
	 */
	public void moveToFIAT(Stage stage) {
		new FIATScreen(stage);
	}
	/**
	 * first opens a CryptoPassDialog and if you enter the password correctly, then it opens a new CryptoScreen
	 */
	public void moveToCrypto(Stage stage, Stage main) {
		new CryptoPassDialog(stage, main, option -> {
			if(option != null) new CryptoScreen(option);
			return null;
		});
	}
	/**
	 * opens a new NotificationScreen
	 */
	public void moveToNotifications(Stage stage){
		new NotificationScreen(stage, screen -> {
			new AccountScreen(screen);
			return null;
		});
	}
	/**
	 * populates list view with the given user's own auctions using multithreading
	 */
	public void populateListView() {
		User current = ((User) AccountManager.getInstance().getCurrentUser());
		int size = current.getOwnAuctions().size();
		if(size > 0) {
			Multithread<AccountScreen> populate = new Multithread<>(this.screen, view -> {
				for(Auction a: AuctionManager.getInstance().getAuctions()) {
					for(int id: current.getOwnAuctions()) {
						if(a.getID() == id) {
							view.getAucs().getItems().add(0, a);
						}
					}
				}
				return null;
			});
			
			populate.start();
		}
	}
}
