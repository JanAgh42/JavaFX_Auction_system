package application.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class InformDialog extends Screen{
	
	private String text;
	
	private Label info = new Label();
	private Button cancel = new Button("Cancel");
	private VBox fields = new VBox(10, info, cancel);
	
	public InformDialog(Stage stage, String text) {
		super(stage, 300, 150);
		
		this.text = text;
		this.setUpLayout();
		this.setUpController();
		this.display("Information");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
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
		this.info.setText(this.text);
		this.fields.setAlignment(Pos.CENTER);
		this.addElement(this.fields);
	}
}
