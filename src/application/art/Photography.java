package application.art;

import application.tools.Constants;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class Photography extends VisualArt{

	private static final long serialVersionUID = -5337475630005827255L;
	private String cameraType;
	
	public Photography(String cameraType, int year) {
		super(year);
		
		this.cameraType = cameraType;
	}
	/**
	 * returns a VBox object populated with the given item-type's data
	 */
	@Override
	public VBox displayArtInfo() {
		
		Label year = new Label("Year:    " + this.year);
		
		Label camera = new Label("Camera type:    " + this.cameraType);
		
		VBox fields = new VBox(10, year, camera);
		fields.setAlignment(Pos.CENTER);
		
		return fields;
	}
	/**
	 * returns a given constant from the Constants class corresponding to this item's type
	 */
	@Override
	public String toString() {
		return Constants.PHOTOGRAPHY;
	}
}
