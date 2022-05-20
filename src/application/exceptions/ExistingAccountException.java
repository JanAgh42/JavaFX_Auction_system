package application.exceptions;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class ExistingAccountException extends Exception{

	private static final long serialVersionUID = -3094883352832311861L;

	public ExistingAccountException(String message) {
		super(message);
	}
}
