package application.gui;

import application.controllers.FundsDialogController;
import application.tools.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class AddFundsDialog extends Screen{
	
	private FundsDialogController cont;
	
	private String type;
	private Label info = new Label();
	private TextField amount = new TextField();
	private Button accept = new Button("Confirm");
	private Button cancel = new Button("Cancel");
	private HBox input = new HBox(10, new Label("Enter amount: "), amount);
	private HBox buttons = new HBox(10, accept, cancel);
	private VBox fields = new VBox(10, input, buttons, info);

	public AddFundsDialog(Stage stage, String type) {
		super(stage, 350, 200);
		
		this.type = type;
		
		this.setUpLayout();
		this.setUpController();
		this.display(this.type.equals(Constants.FIAT) ? "Add FIAT funds" : "Add crypto funds");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new FundsDialogController(this);
		
		this.accept.setOnAction(e -> {
			this.cont.addFunds(this.amount.getText(), this.stage, this.type);
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
		this.stage.initModality(Modality.APPLICATION_MODAL);
		this.fields.setPadding(new Insets(10, 10, 10, 10));
		this.input.setAlignment(Pos.CENTER);
		this.buttons.setAlignment(Pos.CENTER);
		this.fields.setAlignment(Pos.CENTER);
		this.addElement(fields);
	}
	
	public void setLabel(String text) {
		this.info.setText(text);
	}
}
