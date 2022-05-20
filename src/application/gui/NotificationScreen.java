package application.gui;

import application.controllers.NotificationController;
import application.controllers.Outcome;
import application.tools.Notification;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class NotificationScreen extends Screen{
	
	private NotificationController cont;
	
	private Button account = new Button("Return to account");
	private ListView<Notification<?>> ownNotifs = new ListView<>();
	private VBox fields = new VBox(10, ownNotifs, account);
	private Outcome<Stage> option;

	public NotificationScreen(Stage stage, Outcome<Stage> option) {
		super(stage);
		
		this.option = option;
		
		this.setUpLayout();
		this.setUpController();
		this.display("Your notifications");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new NotificationController(this, this.option);
		
		this.account.setOnAction(e -> {
			this.cont.moveToAccount(this.stage);
		});
		
		this.ownNotifs.getSelectionModel().selectedItemProperty().addListener(e -> {
			this.cont.openSelectedNotification();
		});
	}
	/**
	 * sets up the base layout of this screen
	 */
	@Override
	protected void setUpLayout() {
		this.fields.setPadding(new Insets(10, 10, 10, 10));
		this.fields.setAlignment(Pos.CENTER);
		this.addElement(this.fields);
	}
	
	@Override
	public <S> void inform(S object) {
		this.cont.clearListView();
		this.cont.populateListView();
	}
	
	public ListView<Notification<?>> getNotifs() {
		return this.ownNotifs;
	}
}
