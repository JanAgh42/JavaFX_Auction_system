package application.gui;

import application.controllers.ItemTypeController;
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
public class ItemTypeScreen extends Screen{
	
	private NewAucScreen screen;
	private String type;
	private ItemTypeController cont;
	
	private Label info = new Label();
	private Button cancel = new Button("Cancel");
	private Button set = new Button("Set");
	private TextField year = new TextField();
	private TextField country = new TextField();
	private TextField input1 = new TextField();
	private TextField input2 = new TextField();
	private TextField input3 = new TextField();
	private HBox yearInput = new HBox(new Label("Year: "), year);
	private HBox countryInput = new HBox(new Label("Country: "), country);
	private HBox buttons = new HBox(10, set, cancel);
	private HBox inp1, inp2, inp3;
	private VBox fields;
 
	public ItemTypeScreen(Stage stage, NewAucScreen screen, String type) {
		super(stage, 450, 450);
		
		this.screen = screen;
		this.type = type;
		
		this.setUpLayout();
		this.setUpController();
		this.display(this.type + " details");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new ItemTypeController();
		
		this.set.setOnAction(e -> {
			try {
				int year = Integer.parseInt(this.year.getText());
				String country = this.country.getText();
				String s1 = this.input1.getText();
				String s2 = this.input2.getText();
				String s3 = this.input3.getText();
				
				this.screen.setArt(this.cont.setItem(this.type, year, country, s1, s2, s3));
				this.stage.close();
			} catch(NumberFormatException nfe) {
				this.info.setText("Wrong input type");
			}
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
		if(this.type.equals(Constants.CERAMICS)) this.ceramicsLayout();
		else if(this.type.equals(Constants.PAINTING)) this.paintingLayout();
		else if(this.type.equals(Constants.SCULPTURE)) this.sculptureLayout();
		else this.photographyLayout();
		
		this.stage.initModality(Modality.APPLICATION_MODAL);
		this.fields.setPadding(new Insets(10, 10, 10, 10));
		this.yearInput.setAlignment(Pos.CENTER);
		this.countryInput.setAlignment(Pos.CENTER);
		this.inp1.setAlignment(Pos.CENTER);
		this.buttons.setAlignment(Pos.CENTER);
		this.fields.setAlignment(Pos.CENTER);
		this.addElement(this.fields);
	}
	
	private void ceramicsLayout() {
		this.inp1 = new HBox(new Label("Region: "), this.input1);
		this.inp2 = new HBox(new Label("Number of items in set: "), this.input2);
		this.fields = new VBox(10, this.yearInput, this.countryInput, this.inp1, this.inp2, this.buttons, this.info);
		this.inp2.setAlignment(Pos.CENTER);
	}
	
	private void paintingLayout() {
		this.inp1 = new HBox(new Label("Artist: "), this.input1);
		this.inp2 = new HBox(new Label("Dimensions (in mm): "), this.input2);
		this.inp3 = new HBox(new Label("Canvas type: "), this.input3);
		this.fields = new VBox(10, this.yearInput, this.countryInput, this.inp1, this.inp2, this.inp3, this.buttons, this.info);
		this.inp2.setAlignment(Pos.CENTER);
		this.inp3.setAlignment(Pos.CENTER);
	}
	
	private void sculptureLayout() {
		this.inp1 = new HBox(new Label("Sculptor: "), this.input1);
		this.inp2 = new HBox(new Label("Weight (in kg): "), this.input2);
		this.inp3 = new HBox(new Label("Material: "), this.input3);
		this.fields = new VBox(10, this.yearInput, this.countryInput, this.inp1, this.inp2, this.inp3, this.buttons, this.info);

		this.inp2.setAlignment(Pos.CENTER);
		this.inp3.setAlignment(Pos.CENTER);}
	
	private void photographyLayout() {
		this.inp1 = new HBox(new Label("Camera type: "), this.input1);
		this.fields = new VBox(10, this.yearInput, this.inp1, this.buttons, this.info);
	}
}
