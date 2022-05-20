package application.controllers;

import application.art.Ceramics;
import application.art.Painting;
import application.art.Photography;
import application.art.Sculpture;
import application.art.VisualArt;
import application.tools.Constants;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class ItemTypeController {

	public ItemTypeController() {}
	/**
	 * creates and returns an object of the selected type
	 */
	public VisualArt setItem(String type, int year, String country, String s1, String s2, String s3) throws NumberFormatException {
		
		if(type.equals(Constants.CERAMICS)) return new Ceramics(s1, Integer.parseInt(s2), country, year);
		else if(type.equals(Constants.PAINTING)) return new Painting(s1, s2, s3, country, year);
		else if(type.equals(Constants.SCULPTURE)) return new Sculpture(s1, Double.parseDouble(s2), s3, country, year);
		else return new Photography(s1, year);
	}
}
