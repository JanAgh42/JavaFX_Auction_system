package application.payment;

import application.exceptions.NotEnoughFundsException;
import application.users.User;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class FIATPayment implements PaymentStrategy{
	
	public FIATPayment() {}
	/**
	 * subtracts the given amount of money from the sender and adds it to the receiver's account
	 */
	@Override
	public void manageTransaction(User sender, User receiver, double funds) throws NotEnoughFundsException{
		sender.getFiatWallet().subtractFunds(funds);
		receiver.getFiatWallet().addFunds(funds);
	}
}
