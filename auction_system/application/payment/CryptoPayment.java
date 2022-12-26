package application.payment;

import application.exceptions.NotEnoughFundsException;
import application.users.User;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class CryptoPayment implements PaymentStrategy{
	
	public CryptoPayment() {}
	/**
	 * subtracts the given amount of crypto from the sender and adds it to the receiver's account 
	 */
	@Override
	public void manageTransaction(User sender, User receiver, double funds) throws NotEnoughFundsException{
		sender.getCryptoWallet().subtractFunds(funds);
		receiver.getCryptoWallet().addCryptos(funds);
	}
}
