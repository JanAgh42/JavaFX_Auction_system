package application.payment;

import application.exceptions.NotEnoughFundsException;
import application.users.User;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public interface PaymentStrategy {
	
	public void manageTransaction(User sender, User receiver, double funds) throws NotEnoughFundsException;
}
