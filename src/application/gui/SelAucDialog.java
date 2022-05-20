package application.gui;

import application.auction.Auction;
import application.auction.TargetPriceAuction;
import application.controllers.SelAucController;
import application.tools.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class SelAucDialog extends Screen{
	
	private SelAucController cont;
	
	private Auction auction;
	private BrowseAucScreen screen;
	private Label aucName = new Label();
	private Label itemType = new Label();
	private Label curPrice = new Label();
	private Label aucType = new Label();
	private Label info = new Label();
	private TextField newPrice = new TextField();
	private CheckBox crypto = new CheckBox("Pay in crypto");
	private Button cancel = new Button("Cancel");
	private Button bid = new Button("Send offer");
	private HBox buttons = new HBox(10, bid, cancel);
	private HBox input = new HBox(10, new Label("Your offer: "), newPrice);
	private VBox itemData, fields;
	
	public SelAucDialog(Stage stage, BrowseAucScreen screen, Auction auction) {
		super(stage, 450, 500);
		
		this.auction = auction;
		this.screen = screen;
		this.setUpLayout();
		this.setUpController();
		this.display("Auction details");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new SelAucController(this, this.auction);
		
		this.bid.setOnAction(e -> {
			this.cont.sendOffer(this.crypto.isSelected(), this.newPrice.getText(), this.screen, this.stage);
		});
		
		this.cancel.setOnAction(e -> {
			this.stage.close();
		});
	}
	/**
	 * sets up the base layout of this screen
	 */
	public void setUpLayout(boolean pass) {
		String type = this.auction instanceof TargetPriceAuction ? Constants.TARGET : Constants.TIMED;
		this.aucName.setText("Auction name:		" + this.auction.getName());
		this.aucType.setText("Auction type:		" + type);
		this.itemType.setText("Item type:		" + this.auction.getItemType());
		this.curPrice.setText("Current price:		" + this.auction.getCurPrice() + "€");
		this.itemData = this.auction.getItemType().displayArtInfo();
		
		this.fields = pass ? new VBox(10, aucName, aucType, itemType, itemData, curPrice, crypto, input, buttons, info) : 
			new VBox(10, aucName, aucType, itemType, itemData, curPrice, input, buttons, info);
		
		this.stage.initModality(Modality.APPLICATION_MODAL);
		this.fields.setPadding(new Insets(10, 10, 10, 10));
		this.buttons.setAlignment(Pos.CENTER);
		this.input.setAlignment(Pos.CENTER);
		this.fields.setAlignment(Pos.CENTER);
		this.addElement(this.fields);
	}
	
	public BrowseAucScreen getList() {
		return this.screen;
	}
	
	public void setLabel(String text) {
		this.info.setText(text);
	}
}
