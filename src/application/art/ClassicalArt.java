package application.art;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
abstract public class ClassicalArt extends VisualArt{

	private static final long serialVersionUID = 2940721891081468903L;
	protected String country;
	
	public ClassicalArt(String country, int year) {
		super(year);
		
		this.country = country;
	}
}
