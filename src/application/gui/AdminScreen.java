package application.gui;

import application.controllers.AdminController;
import application.users.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class AdminScreen extends Screen{
	
	private AdminController cont;
	
	private Button logout = new Button("Log out");
	private Button group = new Button("Send group message");
	private Button notifs = new Button("Check notifications");
	private ListView<User> users = new ListView<>();
	private HBox buttons = new HBox(10, notifs, group, logout);
	private VBox fields = new VBox(10, users, buttons);
	
	public AdminScreen(Stage stage) {
		super(stage);
		
		this.setUpLayout();
		this.setUpController();
		this.display("Admin control panel");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new AdminController(this);
		
		this.logout.setOnAction(e -> {
			this.cont.logout(this.stage);
		});
		
		this.notifs.setOnAction(e -> {
			this.cont.moveToNotifications(this.stage);
		});
		
		this.group.setOnAction(e -> {
			this.cont.sendGroupMessage();
		});
		
		this.users.getSelectionModel().selectedItemProperty().addListener(e -> {
			this.cont.openUserDialog();
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
	
	public ListView<User> getAccs(){
		return this.users;
	}
}
