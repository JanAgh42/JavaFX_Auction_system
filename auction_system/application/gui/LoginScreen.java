package application.gui;

import application.controllers.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
public class LoginScreen extends Screen{
	
	private LoginController cont;
	
	private Button login = new Button("Login");
	private Button register = new Button("Go to register");
	private TextField email = new TextField();
	private PasswordField password = new PasswordField();
	private Label info = new Label();
	private HBox emailInput = new HBox(42, new Label("Email:"), email);
	private HBox passInput = new HBox(20, new Label("Password:"), password);
	private HBox buttons = new HBox(10, login, register);
	private VBox fields = new VBox(10, emailInput, passInput, buttons, info);

	public LoginScreen(Stage stage) {
		super(stage);
		
		this.setUpLayout();
		this.setUpController();
		this.display("Log In");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new LoginController(this);
		
		this.login.setOnAction(e -> {
			String email = this.email.getText();
			String password = this.password.getText();
			
			this.cont.logIn(this.stage, email, password);
		});
		
		this.register.setOnAction(e -> {
			this.cont.moveToRegister(this.stage);
		});
	}
	/**
	 * sets up the base layout of this screen
	 */
	@Override
	protected void setUpLayout() {
		this.fields.setPadding(new Insets(10, 10, 10, 10));
		this.emailInput.setAlignment(Pos.CENTER);
		this.passInput.setAlignment(Pos.CENTER);
		this.buttons.setAlignment(Pos.CENTER);
		this.fields.setAlignment(Pos.CENTER);
		this.addElement(fields);
	}
	
	public void setLabel(String text) {
		this.info.setText(text);
	}
}
