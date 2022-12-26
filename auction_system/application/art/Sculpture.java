package application.art;

import application.tools.Constants;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class Sculpture extends ClassicalArt{
	
	private static final long serialVersionUID = 8857218136815372891L;
	private double weight;
	private String sculptor;
	private String material;
	
	public Sculpture(String sculptor, double weight, String material, String country, int year) {
		super(country, year);
		
		this.weight = weight;
		this.sculptor = sculptor;
		this.material = material;
	}
	/**
	 * returns a VBox object populated with the given item-type's data
	 */
	@Override
	public VBox displayArtInfo() {
		
		Label year = new Label("Year:    " + this.year);
		Label country = new Label("Country:    " + this.country);
		
		Label weight = new Label("Weight:    " + this.weight + " kg");
		Label sculptor = new Label("Sculptor:    " + this.sculptor);
		Label material = new Label("Material:    " + this.material);
		
		VBox fields = new VBox(10, year, country, weight, sculptor, material);
		fields.setAlignment(Pos.CENTER);
		
		return fields;
	}
	/**
	 * returns a given constant from the Constants class corresponding to this item's type
	 */
	@Override
	public String toString() {
		return Constants.SCULPTURE;
	}
}
