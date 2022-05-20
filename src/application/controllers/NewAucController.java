package application.controllers;

import application.gui.BrowseAucScreen;
import application.gui.ItemTypeScreen;
import application.gui.MenuScreen;
import application.gui.NewAucScreen;
import application.managers.AccountManager;
import application.managers.AuctionManager;
import application.tools.Constants;
import application.users.User;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import application.art.VisualArt;
import application.auction.TargetPriceAuction;
import application.auction.TimedAuction;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class NewAucController {
	/**
	 * @author Jan Agh
	 * @version 28.04.2022
	 * nested inner class for generating unique auction IDs
	 */
	static final class AuctionIDGenerator {
		
		private User user;
		
		private AuctionIDGenerator(User user) {
			this.user = user;
		}
		/**
		 * generates an unique ID for the given auction
		 */
		public int generateID() {
			
			int size = AuctionManager.getInstance().getAuctions().size();
			
			int lastID = size == 0 ? 0 : AuctionManager.getInstance().getAuctions().get(size - 1).getID();
			
			this.user.getOwnAuctions().add(++lastID);
			return lastID;
		}
	}
	
	private NewAucScreen screen;
	private AuctionIDGenerator generator;
	
	public NewAucController(NewAucScreen screen) {
		this.screen = screen;
		this.generator = new AuctionIDGenerator((User) AccountManager.getInstance().getCurrentUser());
		
		this.screen.getType().getItems().add(Constants.PAINTING);
		this.screen.getType().getItems().add(Constants.SCULPTURE);
		this.screen.getType().getItems().add(Constants.CERAMICS);
		this.screen.getType().getItems().add(Constants.PHOTOGRAPHY);
		
		this.screen.getType2().getItems().add(Constants.TIMED);
		this.screen.getType2().getItems().add(Constants.TARGET);
	}
	/**
	 * displays more input fields after selection
	 */
	public void displayMoreFields(VBox fields, HBox timed, HBox target, Button publish, Label info, CheckBox crypto) {
		if(((String) this.screen.getType2().getValue()).equals(Constants.TARGET)) {
			if(fields.getChildren().contains(timed)) fields.getChildren().set(4, target);
			if(!fields.getChildren().contains(target)) fields.getChildren().add(target);
		}
		else if(((String) this.screen.getType2().getValue()).equals(Constants.TIMED)) {
			if(fields.getChildren().contains(target)) fields.getChildren().set(4, timed);
			if(!fields.getChildren().contains(timed)) fields.getChildren().add(timed);
		}
		if(((User) AccountManager.getInstance().getCurrentUser()).getCryptoWallet() != null) {
			if(!fields.getChildren().contains(crypto)) fields.getChildren().add(crypto);
		}
		if(!fields.getChildren().contains(publish)) fields.getChildren().add(publish);
		if(!fields.getChildren().contains(info)) fields.getChildren().add(info);
	}
	/**
	 * opens a new MenuScreen
	 */
	public void moveToMenu(Stage stage) {
		new MenuScreen(stage);
	}
	/**
	 * opens a new ItemTypeScreen
	 */
	public void createItem() {
		String item = this.screen.getType().getSelectionModel().getSelectedItem();
		new ItemTypeScreen(new Stage(), this.screen, item);
	}
	/**
	 * creates a new Auction object based on the entered parameters
	 */
	public void createAuction(Stage stage, String name, VisualArt art, String aucType, boolean crypto) {
	
		if(((String) this.screen.getType2().getValue()).equals(Constants.TARGET)) {
			double price, cryptoPrice = -1;
			
			try {
				price = Double.parseDouble(this.screen.getAmount());
	
				if(crypto) cryptoPrice = Math.round((price / 3000.0) * 100);
				if(cryptoPrice != -1) cryptoPrice /= 100;
				this.screen.setLabel("");
				int ID = this.generator.generateID();
				String owner = AccountManager.getInstance().getCurrentUser().getEmail();
				AuctionManager.getInstance().addNewAuction(new TargetPriceAuction(ID, name, art, price, cryptoPrice, owner));
				new BrowseAucScreen(stage);
				
			} catch(NumberFormatException e) {
				this.screen.setLabel("Wrong input type");
			}
		}
		else {
			
			try {
				LocalDate date = this.screen.getDate().getValue(), now = LocalDate.now();
				if(date == null || date.compareTo(now) < 0) this.screen.setLabel("Invalid date.");
				else {
					this.screen.setLabel("");
					int ID = this.generator.generateID();
					String owner = AccountManager.getInstance().getCurrentUser().getEmail();
					AuctionManager.getInstance().addNewAuction(new TimedAuction(ID, name, art, date, crypto, owner));
					new BrowseAucScreen(stage);
				}
			} catch(DateTimeParseException e) {
				this.screen.setLabel("Invalid date.");
			}	
		}
	}
}
