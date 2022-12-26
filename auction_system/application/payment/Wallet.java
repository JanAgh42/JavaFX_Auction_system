package application.payment;

import java.io.Serializable;

import application.exceptions.NotEnoughFundsException;
import application.gui.Screen;
import application.observer.Subject;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
abstract public class Wallet extends Subject<Screen> implements Serializable{
	
	private static final long serialVersionUID = -6414847204618496503L;
	protected double funds;
	
	public Wallet(double funds) {
		this.funds = funds;
	}
	
	public double getFunds() {
		return this.funds;
	}
	
	abstract public void subtractFunds(double f) throws NotEnoughFundsException;
}
