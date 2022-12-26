package application.tools;

import java.io.Serializable;

import application.controllers.Outcome;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class Notification<T> implements Serializable{
	
	private static final long serialVersionUID = -9027179032172771018L;
	private final String message;
	private final String date;
	private final Outcome<T> option;
	private final T object;

	public Notification(String message, String date, T object, Outcome<T> option) {
		this.message = message;
		this.date = date;
		this.object = object;
		this.option = option;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public T getObject() {
		return this.object;
	}
	/**
	 * returns some key details about the given notification
	 */
	@Override
	public String toString() {
		return this.date + "    " + this.option.outcome(this.object);
	}
}
