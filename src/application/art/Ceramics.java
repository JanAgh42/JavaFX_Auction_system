package application.art;

import application.tools.Constants;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class Ceramics extends ClassicalArt{
	
	private static final long serialVersionUID = 6148899712377021446L;
	private String region;
	private int numOfItemsInSet;
	
	public Ceramics(String region, int numOfItemsInSet, String country, int year) {
		super(country, year);
		
		this.region = region;
		this.numOfItemsInSet = numOfItemsInSet;
	}
	/**
	 * returns a VBox object populated with the given item-type's data
	 */
	@Override
	public VBox displayArtInfo() {
		
		Label year = new Label("Year:    " + this.year);
		Label country = new Label("Country:    " + this.country);
		
		Label region = new Label("Region:    " + this.region);
		Label items = new Label("Items in set:    " + this.numOfItemsInSet);
		
		VBox fields = new VBox(10, year, country, region, items);
		fields.setAlignment(Pos.CENTER);
		
		return fields;
	}
	/**
	 * returns a given constant from the Constants class corresponding to this item's type
	 */
	@Override
	public String toString() {
		return Constants.CERAMICS;
	}
}
