package application.gui;

import application.controllers.CryptoPassController;
import application.controllers.Outcome;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class CryptoPassDialog extends Screen{
	
	private CryptoPassController cont;
	
	private Label info = new Label();
	private PasswordField password = new PasswordField();
	private PasswordField repeat = new PasswordField();
	private Button cancel = new Button("Cancel");
	private Button enter = new Button("Enter");
	private HBox pass1 = new HBox(10, new Label("Enter your password: "), password);
	private HBox pass2 = new HBox(10, new Label("Repeat your password: "), repeat);
	private HBox buttons = new HBox(10, enter, cancel);
	private VBox fields;
	private boolean hasWallet;
	private Outcome<Stage> option;
	private Stage main;

	public CryptoPassDialog(Stage stage, Stage main, Outcome<Stage> option) {
		super(stage, 330, 200);
		
		this.main = main;
		this.option = option;
		this.setUpController();
		this.display("Crypto wallet auth");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new CryptoPassController(this, this.option);
		
		this.cancel.setOnAction(e -> {
			this.stage.close();
		});
		
		this.enter.setOnAction(e -> {
			if(this.hasWallet) this.cont.initCryptoWallet(this.password.getText(), this.stage, this.main);
			else this.cont.initCryptoWallet(this.password.getText(), this.repeat.getText(), this.stage, this.main);
		});
	}
	/**
	 * sets up the base layout of this screen
	 */
	public void setUpLayout(boolean pass) {
		this.hasWallet = pass;
		this.fields = pass ? new VBox(10, pass1, buttons, info) : new VBox(10, pass1, pass2, buttons, info);
		
		this.fields.setPadding(new Insets(10, 10, 10, 10));
		this.pass1.setAlignment(Pos.CENTER);
		this.pass2.setAlignment(Pos.CENTER);
		this.buttons.setAlignment(Pos.CENTER);
		this.fields.setAlignment(Pos.CENTER);
		this.addElement(this.fields);
	}
	
	public void setFields(VBox fields) {
		this.fields = fields;
	}
	
	public void setLabel(String text) {
		this.info.setText(text);
	}
}
