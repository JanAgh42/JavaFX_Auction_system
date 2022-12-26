package application.gui;

import application.auction.Auction;
import application.controllers.BrowseAucController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class BrowseAucScreen extends Screen {
	
	private BrowseAucController cont;
	
	private ListView<Auction> allAuctions = new ListView<>();
	private Label info = new Label("Click on an auction to see more details!");
	private Button menu = new Button("Return to menu");
	private VBox fields = new VBox(10, info, allAuctions, menu);
	
	public BrowseAucScreen(Stage stage) {
		super(stage);
		
		this.setUpLayout();
		this.setUpController();
		this.display("Browse existing auctions");
	}
	/**
	 * creates a controller object and initializes all event listeners
	 */
	@Override
	protected void setUpController() {
		
		this.cont = new BrowseAucController(this);
		
		this.menu.setOnAction(e -> {
			this.cont.moveToMenu(this.stage);
		});
		
		this.allAuctions.getSelectionModel().selectedItemProperty().addListener(e -> {
			this.cont.openSelectedAuction();
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
		if(this.cont != null) {
			this.cont.clearListView();
			this.cont.populateListView();
		}
	}
	
	public ListView<Auction> getAucs(){
		return this.allAuctions;
	}
}
