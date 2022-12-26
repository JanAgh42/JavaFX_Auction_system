package application.payment;

import application.exceptions.NotEnoughFundsException;
import application.managers.AccountManager;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class FIATWallet extends Wallet{
	
	private static final long serialVersionUID = 7350219800876195077L;
	public FIATWallet() {
		super(0);
	}
	/**
	 * adds money
	 */
	public void addFunds(double funds) {
		this.funds += funds;
		this.update(this.funds);
		AccountManager.getInstance().saveState();
	}
	/**
	 * subtracts money
	 */
	@Override
	public void subtractFunds(double funds) throws NotEnoughFundsException{
		if(this.funds - funds < 0) throw new NotEnoughFundsException("You don't have enough funds!");
		this.funds -= funds;
	}
}
