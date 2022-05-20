package application.controllers;

import application.exceptions.NotEnoughFundsException;
import application.gui.AddFundsDialog;
import application.managers.AccountManager;
import application.tools.Constants;
import application.users.User;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class FundsDialogController {
	
	private AddFundsDialog screen;
	
	public FundsDialogController(AddFundsDialog screen) {
		this.screen = screen;
	}
	/**
	 * adds the given amount and type of funds to the user's wallet
	 */
	public void addFunds(String input, Stage stage, String type) {
		User user = (User) AccountManager.getInstance().getCurrentUser();
		double amount = 0;
		
		try {
			amount = Integer.parseInt(input);
			
			if(type.equals(Constants.CRYPTO) && user.getCryptoWallet() != null) this.addCrypto(amount, user);
			else user.getFiatWallet().addFunds(amount);
			stage.close();
		} catch(NumberFormatException e) {
			this.screen.setLabel("Wrong input type");
		} catch(NotEnoughFundsException nefe) {
			nefe.setFunctionality(message -> {
				this.screen.setLabel(message);
				return null;
			});
			nefe.printStackTrace();
		}
	}
	/**
	 * subtracts funds from the user's regular wallet and adds a corresponding ammount of cryptos to his crypto wallet
	 */
	private void addCrypto(double crypto, User user) throws NotEnoughFundsException{
		double euros = crypto * 3000;
		
		if(user.getFiatWallet().getFunds() - euros < 0) throw new NotEnoughFundsException("Not enough funds on your FIAT account!");
		user.getFiatWallet().subtractFunds(euros);
		user.getCryptoWallet().addCryptos(crypto);
	}
}
