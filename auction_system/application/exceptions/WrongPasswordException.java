package application.exceptions;

import application.users.User;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class WrongPasswordException extends Exception{
	
	private static final long serialVersionUID = -5962386871169889710L;
	private User user;

	public WrongPasswordException(String message, User user) {
		super(message);
		
		this.user = user;
	}
	/**
	 * returns the right message based on the number of remaining attempts
	 */
	@Override
	public String getMessage() {
		
		String message = this.calculateAttempts() ? super.getMessage() + user.getAttempts() : "Account has been banned.";
		
		return message;
	}
	/**
	 * calculates how many attempts are left before ban
	 */
	private boolean calculateAttempts() {
		user.decrAttempts();
		return user.getAttempts() > 0 ? true : false;
	}
}
