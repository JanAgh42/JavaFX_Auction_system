package application.gui;


import application.art.VisualArt;
import application.controllers.NewAucController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class NewAucScreen extends Screen{
	
	private NewAucController cont;
	
	private VisualArt art;
	private Label info = new Label();
	private TextField name = new TextField();
	private TextField amount = new TextField();
	private DatePicker time = new DatePicker();
	private Button publish = new Button("Create Auction");
	private Button back = new Button("Return to menu");
	private CheckBox crypto = new CheckBox("Allow crypto payments");
	private HBox timed = new HBox(23, new Label("Expiration date: "), time);
	private HBox target = new HBox(40, new Label("Target price: "), amount);
	private HBox input = new HBox(30, new Label("Auction name: "), name);
	private ChoiceBox<String> type = new ChoiceBox<>();
	private ChoiceBox<String> type2 = new ChoiceBox<>();
	private HBox choice = new HBox(100, new Label("Art type: "), type);
	private HBox choice2 = new HBox(47, new Label("Auction type: "), type2);
	private VBox fields = new VBox(10, back, input, choice, choice2);
	
	public NewAucScreen(Stage stage) {
		super(stage);
		
		this.setUpLayout();
		this.setUpController();
		this.display("Create a new auction");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new NewAucController(this);
		
		this.type2.setOnAction(e -> {
			this.cont.displayMoreFields(this.fields, this.timed, this.target, this.publish, this.info, this.crypto);
		});
		
		this.back.setOnAction(e -> {
			this.cont.moveToMenu(this.stage);
		});
		
		this.type.getSelectionModel().selectedItemProperty().addListener(e -> {
			this.cont.createItem();
		});
		
		this.publish.setOnAction(e -> {
			
			String name = this.name.getText();
			String aucType = this.type2.getValue();
			boolean cryptoPay = this.crypto.isSelected();
			
			this.cont.createAuction(this.stage, name, this.art, aucType, cryptoPay);
		});
	}
	/**
	 * sets up the base layout of this screen
	 */
	@Override
	protected void setUpLayout() {
		this.fields.setPadding(new Insets(10, 10, 10, 10));
		this.timed.setAlignment(Pos.CENTER);
		this.target.setAlignment(Pos.CENTER);
		this.input.setAlignment(Pos.CENTER);
		this.choice.setAlignment(Pos.CENTER);
		this.choice2.setAlignment(Pos.CENTER);
		this.fields.setAlignment(Pos.CENTER);
		this.addElement(fields);
	}
	
	public ChoiceBox<String> getType(){
		return this.type;
	}
	
	public ChoiceBox<String> getType2(){
		return this.type2;
	}
	
	public void setLabel(String text) {
		this.info.setText(text);
	}
	
	public void setArt(VisualArt art) {
		this.art = art;
	}
	
	public DatePicker getDate() {
		return this.time;
	}
	
	public String getAmount() {
		return this.amount.getText();
	}
}
