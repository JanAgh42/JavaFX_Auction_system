package application.payment;

import application.exceptions.NotEnoughFundsException;
import application.managers.AccountManager;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class CryptoWallet extends Wallet{
	
	private static final long serialVersionUID = 4469404020490688855L;
	private String password;
	
	public CryptoWallet(String password) {
		super(0);
		
		this.password = password;
	}
	/**
	 * adds crypto
	 */
	public void addCryptos(double funds) {
		this.funds += funds;
		this.update(this.funds);
		AccountManager.getInstance().saveState();
	}
	/**
	 * subtracts crypto
	 */
	@Override
	public void subtractFunds(double funds) throws NotEnoughFundsException {
		if(this.funds - funds < 0) throw new NotEnoughFundsException("You don't have enough funds!");
		this.funds -= funds;
	}
	/**
	 * returns the password of the user's crypto wallet
	 */
	public String getPassword() {
		return this.password;
	}
}
