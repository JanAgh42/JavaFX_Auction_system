package application.gui;

import application.controllers.RegisterController;
import application.exceptions.ExistingAccountException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class RegisterScreen extends Screen{
	
	private RegisterController cont;
	
	private Button login = new Button("Back to login");
	private Button register = new Button("Create new account");
	private TextField name = new TextField();
	private TextField email = new TextField();
	private PasswordField password = new PasswordField();
	private PasswordField reppass = new PasswordField();
	private CheckBox admin = new CheckBox("Admin account");
	private Label info = new Label();
	private HBox nameInput = new HBox(64, new Label("Name:"), name);
	private HBox emailInput = new HBox(66, new Label("Email:"), email);
	private HBox passInput = new HBox(45, new Label("Password:"), password);
	private HBox passInput2 = new HBox(5, new Label("Repeat password:"), reppass);
	private HBox buttons = new HBox(10, login, register);
	private VBox fields = new VBox(10, nameInput, emailInput, passInput, passInput2, admin, buttons, info);
	
	public RegisterScreen(Stage stage) {
		super(stage);
		
		this.setUpLayout();
		this.setUpController();
		this.display("Register");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new RegisterController(this);
		
		this.login.setOnAction(e -> {
			this.cont.moveToLogin(this.stage);
		});
		
		this.register.setOnAction(e -> {
			String name = this.name.getText();
			String email = this.email.getText();
			String password = this.password.getText();
			String reppass = this.reppass.getText();
			boolean selected = this.admin.isSelected();
			
			try {
				this.cont.register(this.stage, name, email, password, reppass, selected);
			} catch(ExistingAccountException eae) {
				this.info.setText(eae.getMessage());
			}
		});
	}
	/**
	 * sets up the base layout of this screen
	 */
	@Override
	protected void setUpLayout() {
		this.fields.setPadding(new Insets(10, 10, 10, 10));
		this.nameInput.setAlignment(Pos.CENTER);
		this.emailInput.setAlignment(Pos.CENTER);
		this.passInput.setAlignment(Pos.CENTER);
		this.passInput2.setAlignment(Pos.CENTER);
		this.buttons.setAlignment(Pos.CENTER);
		this.fields.setAlignment(Pos.CENTER);
		this.addElement(this.fields);
	}
	
	public void setLabel(String text) {
		this.info.setText(text);
	}
}
