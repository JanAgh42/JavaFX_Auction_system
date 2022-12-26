package application.art;

import java.io.Serializable;
import javafx.scene.layout.VBox;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
abstract public class VisualArt implements Serializable{

	private static final long serialVersionUID = -7113866141636023472L;
	protected int year;
	
	public VisualArt(int year) {
		this.year = year;
	}
	
	abstract public VBox displayArtInfo();
}
