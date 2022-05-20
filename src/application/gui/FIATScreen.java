package application.gui;

import application.controllers.FIATController;
import application.observer.Observer;
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
public class FIATScreen extends Screen implements Observer{
	
	private FIATController cont;
	
	private Label funds = new Label();
	private Button account = new Button("Return to account");
	private Button add = new Button("Add funds");
	private HBox buttons = new HBox(10, account, add);
	private VBox fields = new VBox(10, funds, buttons);
	
	public FIATScreen(Stage stage) {
		super(stage);
		
		this.setUpLayout();
		this.setUpController();
		this.display("FIAT Wallet");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {

		this.cont = new FIATController(this);
		
		this.account.setOnAction(e -> {
			this.cont.moveToAccount(this.stage);
		});
		
		this.add.setOnAction(e -> {
			this.cont.openFIATDialog();
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
	
	public void setLabel(String text) {
		this.funds.setText(text);
	}

	@Override
	public <S> void inform(S object) {
		this.funds.setText("Current balance: " + object + "€");
	}
}
