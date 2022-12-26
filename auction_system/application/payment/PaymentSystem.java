package application.payment;

import application.exceptions.NotEnoughFundsException;
import application.users.User;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class PaymentSystem {
	
	private static PaymentSystem system;
	
	private PaymentSystem() {
	}
	/**
	 * Singleton class - return the existing instance
	 */
	public static PaymentSystem getInstance() {
		if(system == null) system = new PaymentSystem();
		return system;
	}
	/**
	 * Strategy design pattern - executes the given payment strategy based on the received PaymentStrategy object
	 */
	public void handleTransaction(User sender, User receiver, double funds, PaymentStrategy strategy) throws NotEnoughFundsException{
		strategy.manageTransaction(sender, receiver, funds);
	}
}
