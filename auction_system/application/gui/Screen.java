package application.gui;

import application.observer.Observer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
abstract public class Screen implements Observer{
	
	protected StackPane root;
	protected Scene scene;
	protected Stage stage;
	
	public Screen(Stage stage) {
		this(stage, 800, 600);
	}
	
	public Screen(Stage stage, double width, double height) {
		this.stage = stage;
		this.root = new StackPane();
		this.scene = new Scene(root, width, height);
	}
	
	public Pane getRoot() {
		return this.root;
	}
	
	protected void display(String name) {
		//this.scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		this.stage.setTitle(name);
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	protected void addElement(Node node) {
		this.root.getChildren().add(node);
	}
	
	@Override
	public <S> void inform(S object) {}
	
	abstract protected void setUpController();
	protected void setUpLayout() {};
}
