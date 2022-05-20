package application.gui;

import application.auction.Auction;
import application.controllers.AccountController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class AccountScreen extends Screen{
	
	private AccountController cont;
	
	private Label name = new Label(); 
	private Label email = new Label();
	private Label auc = new Label();
	private Button menu = new Button("Return to menu");
	private Button notif = new Button("Read notifications");
	private Button fiat = new Button("FIAT wallet");
	private Button crypto = new Button("Crypto wallet");
	private ListView<Auction> ownAuctions = new ListView<>();
	private HBox buttons = new HBox(10, menu, fiat, crypto, notif);
	private VBox fields = new VBox(10, name, email, auc, ownAuctions, buttons);
	
	public AccountScreen(Stage stage) {
		super(stage);
		
		this.setUpLayout();
		this.setUpController();
		this.display("Your account");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new AccountController(this);
		
		this.menu.setOnAction(e -> {
			this.cont.moveToMenu(this.stage);
		});
		
		this.fiat.setOnAction(e -> {
			this.cont.moveToFIAT(this.stage);
		});
		
		this.crypto.setOnAction(e -> {
			this.cont.moveToCrypto(new Stage(), this.stage);
		});
		
		this.notif.setOnAction(e -> {
			this.cont.moveToNotifications(this.stage);
		});
	}
	/**
	 * sets up the base layout of this screen
	 */
	@Override
	protected void setUpLayout() {
		this.fields.setPadding(new Insets(10, 10, 10, 10));
		this.buttons.setAlignment(Pos.CENTER);
		this.fields.setAlignment(Pos.CENTER);
		this.addElement(this.fields);
	}	
	
	public ListView<Auction> getAucs(){
		return this.ownAuctions;
	}
	
	public void setName(String name) {
		this.name.setText(name);
	}
	
	public void setEmail(String email) {
		this.email.setText(email);
	}
	
	public void setAucAmount(String size) {
		this.auc.setText(size);
	}
}
