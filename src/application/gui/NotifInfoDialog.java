package application.gui;

import application.controllers.NotifDialogController;
import application.tools.Notification;
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
public class NotifInfoDialog extends Screen{
	
	private NotifDialogController cont;
	private Notification<?> notification;
	private Button cancel = new Button("Cancel");
	private Button reply = new Button("Reply");
	private Button delete = new Button("Delete");
	private Label name = new Label();
	private Label info = new Label();
	private Label notif = new Label();
	private HBox buttons = new HBox(10, reply, delete, cancel);
	private VBox fields = new VBox(10, name, info, buttons, notif);

	public NotifInfoDialog(Stage stage, Notification<?> notification) {
		super(stage, 400, 150);
		
		this.notification = notification;
		
		this.setUpLayout();
		this.setUpController();
		this.display("Notification details");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new NotifDialogController(this, this.notification);
		
		this.delete.setOnAction(e -> {
			this.cont.removeSeenNotif(this.stage);
		});
		
		this.reply.setOnAction(e -> {
			this.cont.reply();
		});
		
		this.cancel.setOnAction(e -> {
			this.stage.close();
		});
	}
	/**
	 * sets up the base layout of this screen
	 */
	@Override
	protected void setUpLayout() {
		this.fields.setPadding(new Insets(10, 10, 10, 10));
		this.info.setText(this.notification.getMessage());
		this.buttons.setAlignment(Pos.CENTER);
		this.fields.setAlignment(Pos.CENTER);
		this.addElement(fields);
	}
	
	public void setName(String text) {
		this.name.setText(text);
	}
	
	public void setLabel(String text) {
		this.notif.setText(text);
	}
}
