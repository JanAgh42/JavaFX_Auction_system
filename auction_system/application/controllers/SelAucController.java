package application.controllers;

import application.auction.Auction;
import application.auction.TargetPriceAuction;
import application.auction.TimedAuction;
import application.exceptions.NotEnoughFundsException;
import application.gui.InformDialog;
import application.gui.CryptoPassDialog;
import application.gui.Screen;
import application.gui.SelAucDialog;
import application.managers.AccountManager;
import application.managers.AuctionManager;
import application.payment.CryptoPayment;
import application.payment.FIATPayment;
import application.payment.PaymentStrategy;
import application.payment.PaymentSystem;
import application.tools.Constants;
import application.users.Account;
import application.users.User;
import javafx.stage.Stage;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class SelAucController {
	
	private SelAucDialog screen;
	
	private Auction auction;

	public SelAucController(SelAucDialog screen, Auction auction) {
		this.screen = screen;
		this.auction = auction;
		
		this.hasCryptoPayment();
	}
	/**
	 * checks if the user can pay for the given auction in cryptos
	 */
	public void hasCryptoPayment() {	
		this.screen.setUpLayout(this.auction.getCryptoPrice() != -1 ? true : false);
	}
	/**
	 * validates and sends a new bid
	 */
	public void sendOffer(boolean crypto, String offer, Screen screen, Stage stage) {
		double amount = 0, cryptoAmount = -1;
		User user = (User) AccountManager.getInstance().getCurrentUser();
		if(crypto && user.getCryptoWallet() == null && this.auction.getCryptoPrice() != -1) {
			this.screen.setLabel("You don't have a crypto wallet!");
		}else {
			try {
				if(this.auction.getCryptoPrice() == -1) amount = Double.parseDouble(offer);
				else {
					if(crypto) {
						cryptoAmount = Double.parseDouble(offer);
						amount = cryptoAmount * 3000;
					}
					else {
						amount = Double.parseDouble(offer);
						cryptoAmount = Math.round((amount / 3000) * 100);
						cryptoAmount /= 100;
					}
				}
				if(user.getOwnAuctions().contains(Integer.valueOf(this.auction.getID()))) this.screen.setLabel("This is your own auction!");
				else {
					if(this.auction.getCurPrice() >= amount) this.screen.setLabel("Too small offer!");
					else {
						this.screen.setLabel("");
						if(!this.auction.findSubscriber((User) AccountManager.getInstance().getCurrentUser())) {
							this.auction.subscribe((User) AccountManager.getInstance().getCurrentUser());
							((User) AccountManager.getInstance().getCurrentUser()).addFollow(this.auction.getID());
						}
						if(this.auction instanceof TargetPriceAuction) this.checkTargetPrice(stage, user, amount, cryptoAmount, crypto);
						else this.updatePrice(stage, user, amount, cryptoAmount, crypto ? Constants.CRYPTO : Constants.FIAT);
					}
				}
			} catch(NumberFormatException e) {
				this.screen.setLabel("Wrong input type");
			} catch(NotEnoughFundsException nefe) {
				
			}
		}
	}
	/**
	 * if it is a TargetPriceAuction, checks if the target price has been reached and if so, handles the transaction
	 */
	private void checkTargetPrice(Stage stage, User user, double amount, double cryptoAmount, boolean crypto) {
		try {
			if(amount >= ((TargetPriceAuction) this.auction).getPrice()) {
				User owner = null;
				for(Account a: AccountManager.getInstance().getAccounts()) {
					if(a.getEmail().equals(this.auction.getOwner())) owner = (User) a;
				}
				final User seller = owner; 
				
				if(crypto) {
					new CryptoPassDialog(new Stage(), null, option -> {
						try {
							this.handleTransaction(stage, user, seller, cryptoAmount, new CryptoPayment());
						} catch(NotEnoughFundsException nefe) {
							nefe.setFunctionality(message -> {
								this.screen.setLabel(message);
								return null;
							});
							nefe.printStackTrace();
						}
						return null;
					});
				}
				else this.handleTransaction(stage, user, seller, amount, new FIATPayment());			
			}
			else this.updatePrice(stage, user, amount, cryptoAmount, crypto ? Constants.CRYPTO : Constants.FIAT);
		} catch (NotEnoughFundsException nefe) {
			nefe.setFunctionality(message -> {
				this.screen.setLabel(message);
				return null;
			});
			nefe.printStackTrace();
		}
	}
	/**
	 * handles the transaction and opens a new InformDialog
	 */
	private void handleTransaction(Stage stage, User buyer, User seller, double funds, PaymentStrategy strategy) throws NotEnoughFundsException {
		PaymentSystem.getInstance().handleTransaction(buyer, seller, funds, strategy);
		this.removeReferences();
		AuctionManager.getInstance().removeAuction(this.auction);
		AccountManager.getInstance().saveState();
		new InformDialog(new Stage(), "You have successfully bought this item!");
		stage.close();
	}
	/**
	 * updates the current price of the given auction 
	 */
	private void updatePrice(Stage stage, User user, double amount, double cryptoAmount, String type) throws NotEnoughFundsException{
		if(type.equals(Constants.FIAT)) {
			if(user.getFiatWallet().getFunds() - amount < 0) throw new NotEnoughFundsException("You don't have enough funds!");
			if(this.auction instanceof TimedAuction) {
				((TimedAuction) this.auction).setBidder(user.getEmail());
				((TimedAuction) this.auction).setMethod(Constants.FIAT);
			}
			this.auction.setCurrentPrice(amount, cryptoAmount);
			this.screen.getList().inform(null);
			AuctionManager.getInstance().saveState();
			AccountManager.getInstance().saveState();
		}
		else if(type.equals(Constants.CRYPTO)) {
			if(user.getCryptoWallet().getFunds() - cryptoAmount < 0) throw new NotEnoughFundsException("You don't have enough funds!");
			if(this.auction instanceof TimedAuction) {
				((TimedAuction) this.auction).setBidder(user.getEmail());
				((TimedAuction) this.auction).setMethod(Constants.CRYPTO);
			}
			this.auction.setCurrentPrice(amount, cryptoAmount);
			this.screen.getList().inform(null);
			AuctionManager.getInstance().saveState();
			AccountManager.getInstance().saveState();
		}
		stage.close();
	}
	/**
	 * removes all references to the given auction from all of the user profiles
	 */
	private void removeReferences() {
		for(Account a: AccountManager.getInstance().getAccounts()) {
			if(a instanceof User) {
				if(((User) a).getFollowed().contains(this.auction.getID())) {
					((User) a).getFollowed().remove(Integer.valueOf(this.auction.getID()));
				}
				if(((User) a).getOwnAuctions().contains(auction.getID())) {
					((User) a).getOwnAuctions().remove(Integer.valueOf(this.auction.getID()));
				}
			}
		}
	}
}
