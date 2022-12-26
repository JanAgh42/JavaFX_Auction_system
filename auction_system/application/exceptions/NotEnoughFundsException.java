package application.exceptions;

import application.controllers.Outcome;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class NotEnoughFundsException extends Exception{
	
	private static final long serialVersionUID = -5491145264860779254L;
	private Outcome<String> option;

	public NotEnoughFundsException(String message) {
		super(message);
		
		this.option = null;
	}
	/**
	 * executes the entered lambda 
	 */
	@Override
	public void printStackTrace() {
		if(this.option != null)  this.option.outcome(this.getMessage());
	}
	/**
	 * receives a lambda that determines the exception's functionality
	 */
	public void setFunctionality(Outcome<String> option) {
		this.option = option;
	}
}
