package application.gui;

import application.controllers.CryptoController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class CryptoScreen extends Screen{
	
	private CryptoController cont;
	
	private Label funds = new Label();
	private Button account = new Button("Return to account");
	private Button add = new Button("Add funds");
	private HBox buttons = new HBox(10, account, add);
	private VBox fields = new VBox(10, funds, buttons);

	public CryptoScreen(Stage stage) {
		super(stage);
		
		this.setUpLayout();
		this.setUpController();
		this.display("Crypto Wallet");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
	
		this.cont = new CryptoController(this);
		
		this.account.setOnAction(e -> {
			this.cont.moveToAccount(this.stage);
		});
		
		this.add.setOnAction(e -> {
			this.cont.openCryptoDialog();
		});
	}
	
	public void setLabel(String text) {
		this.funds.setText(text);
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
	
	@Override
	public <S> void inform(S object) {
		this.funds.setText("Current crypto balance: " + object + " ETH");
	}
}
