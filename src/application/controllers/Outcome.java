package application.controllers;

import java.io.Serializable;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
@FunctionalInterface
public interface Outcome<T> extends Serializable{
	public String outcome(T data);
}
