package application.gui;

import application.controllers.SendNotifController;
import application.users.Account;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class SendNotifDialog extends Screen{

	private SendNotifController cont;
	private Account user;
	
	private Button cancel = new Button("Cancel");
	private Button send = new Button("Send");
	private TextField message = new TextField();
	private HBox buttons = new HBox(10, send, cancel);
	private HBox input = new HBox(10, new Label("Message:"), message);
	private VBox fields = new VBox(10, input, buttons);
	
	public SendNotifDialog(Stage stage, Account user) {
		super(stage, 300, 200);
		
		this.user = user;
		this.setUpLayout();
		this.setUpController();
		this.display("Information");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new SendNotifController(this.user);
		
		this.cancel.setOnAction(e -> {
			this.stage.close();
		});
		
		this.send.setOnAction(e -> {
			if(this.user != null) this.cont.sendMessage(this.stage, this.message.getText());
			else this.cont.sendGroupMessage(this.stage, this.message.getText());
		});
	}
	/**
	 * sets up the base layout of this screen
	 */
	@Override
	protected void setUpLayout() {
		this.fields.setPadding(new Insets(10, 10, 10, 10));
		this.buttons.setAlignment(Pos.CENTER);
		this.input.setAlignment(Pos.CENTER);
		this.fields.setAlignment(Pos.CENTER);
		this.addElement(this.fields);
	}
}
