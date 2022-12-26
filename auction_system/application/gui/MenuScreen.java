package application.gui;

import application.controllers.MenuController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class MenuScreen extends Screen {
	
	private MenuController cont;

	private Button account = new Button("Account");
	private Button newAuction = new Button("Create auction");
	private Button browse = new Button("Browse auctions");
	private Button logout = new Button("Log out");
	private VBox fields = new VBox(10, account, newAuction, browse, logout);
	
	public MenuScreen(Stage stage) {
		super(stage);

		this.setUpLayout();
		this.setUpController();
		this.display("Menu");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new MenuController();
		
		this.account.setOnAction(e -> {
			this.cont.moveToAccount(this.stage);
		});
		
		this.newAuction.setOnAction(e -> {
			this.cont.createAuction(this.stage);
		});
		
		this.browse.setOnAction(e -> {
			this.cont.browseAuctions(this.stage);
		});
		
		this.logout.setOnAction(e -> {
			this.cont.logOut(this.stage);
		});
	}
	/**
	 * sets up the base layout of this screen
	 */
	@Override
	protected void setUpLayout() {
		this.fields.setPadding(new Insets(10, 10, 10, 10));
		this.fields.setAlignment(Pos.CENTER);
		this.addElement(fields);
	}
}
