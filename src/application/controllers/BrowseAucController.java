package application.controllers;

import application.auction.Auction;
import application.gui.BrowseAucScreen;
import application.gui.MenuScreen;
import application.gui.SelAucDialog;
import application.managers.AuctionManager;
import application.tools.Multithread;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class BrowseAucController {
	
	private BrowseAucScreen screen;
	
	public BrowseAucController(BrowseAucScreen screen) {
		this.screen = screen;
		AuctionManager.getInstance().subscribe(this.screen);
		this.populateListView();
	}
	/**
	 * opens a new MenuScreen
	 */
	public void moveToMenu(Stage stage) {
		AuctionManager.getInstance().unsubscribe(this.screen);
		new MenuScreen(stage);
	}
	/**
	 * populates listview with existing auctions using multithreading
	 */
	public void populateListView() {
		Multithread<BrowseAucScreen> populate = new Multithread<>(this.screen, view -> {
			for(Auction a: AuctionManager.getInstance().getAuctions()) {
				this.screen.getAucs().getItems().add(0, a);
			}
			return null;
		});
		populate.start();
	}
	/**
	 * deletes all entries from the given listview
	 */
	public void clearListView() {
		this.screen.getAucs().getItems().clear();
	}
	/**
	 * opens a dialog box with the selected auction's details
	 */
	public void openSelectedAuction() {
		Auction a = this.screen.getAucs().getSelectionModel().getSelectedItem();
		if(a != null) new SelAucDialog(new Stage(), this.screen, a);
	}
}
