package application.art;

import application.tools.Constants;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class Painting extends ClassicalArt{
	
	private static final long serialVersionUID = 561728084236875269L;
	private String artist;
	private String dimensions;
	private String canvasType;
	
	public Painting(String artist, String dimensions, String canvasType, String country, int year) {
		super(country, year);
		
		this.artist = artist;
		this.dimensions = dimensions;
		this.canvasType = canvasType;
	}
	/**
	 * returns a VBox object populated with the given item-type's data
	 */
	@Override
	public VBox displayArtInfo() {

		Label year = new Label("Year:    " + this.year);
		Label country = new Label("Country:    " + this.country);
		
		Label artist = new Label("Artist:    " + this.artist);
		Label dims= new Label("Dimensions:    " + this.dimensions + " mm");
		Label canvas = new Label("Canvas type:    " + this.canvasType);
		
		VBox fields = new VBox(10, year, country, artist, dims, canvas);
		fields.setAlignment(Pos.CENTER);
		
		return fields;
	}
	/**
	 * returns a given constant from the Constants class corresponding to this item's type
	 */
	@Override
	public String toString() {
		return Constants.PAINTING;
	}
}
