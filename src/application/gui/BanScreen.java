package application.gui;

import application.controllers.BanController;
import application.users.User;
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
public class BanScreen extends Screen{
	
	private BanController cont;
	private User user;
	
	private Label info = new Label();
	private Label name = new Label();
	private Label email = new Label();
	private Button cancel = new Button("Cancel");
	private Button ban = new Button();
	private Button notif = new Button("Message");	
	private HBox buttons = new HBox(10, ban, notif, cancel);
	private VBox fields = new VBox(10, name, email, info, buttons);

	public BanScreen(Stage stage, User user) {
		super(stage, 300, 200);
		
		this.user = user;
		
		this.setUpLayout();
		this.setUpController();
		this.display("User information");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new BanController(this, this.user);
		
		this.cancel.setOnAction(e -> {
			this.stage.close();
		});
		
		this.notif.setOnAction(e -> {
			this.cont.sendNotification(this.user);
		});
		
		this.ban.setOnAction(e -> {
			this.cont.banOrUnbanUser();
		});
	}
	/**
	 * sets up the base layout of this screen
	 */
	@Override
	protected void setUpLayout() {
		this.name.setText("Name:   " + this.user.getName());
		this.email.setText("Email:   " + this.user.getEmail());
		
		this.fields.setPadding(new Insets(10, 10, 10, 10));
		this.buttons.setAlignment(Pos.CENTER);
		this.fields.setAlignment(Pos.CENTER);
		this.addElement(this.fields);
	}
	
	public void setLabel(String text) {
		this.info.setText(text);
	}
	
	public void setButton(String text) {
		this.ban.setText(text);
	}
}
