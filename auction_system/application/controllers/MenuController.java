package application.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import application.auction.Auction;
import application.auction.TimedAuction;
import application.exceptions.NotEnoughFundsException;
import application.gui.AccountScreen;
import application.gui.BrowseAucScreen;
import application.gui.LoginScreen;
import application.gui.MenuScreen;
import application.gui.NewAucScreen;
import application.managers.AccountManager;
import application.managers.AuctionManager;
import application.payment.CryptoPayment;
import application.payment.FIATPayment;
import application.payment.PaymentSystem;
import application.tools.Constants;
import application.tools.Multithread;
import application.users.Account;
import application.users.User;
import javafx.stage.Stage;

public class MenuController {
	
	public MenuController() {
		this.checkTimedAuctions();
	}

	public void moveToAccount(Stage stage) {
		new AccountScreen(stage);
	}
	
	public void createAuction(Stage stage) {
		new NewAucScreen(stage);
	}
	
	public void browseAuctions(Stage stage) {
		new BrowseAucScreen(stage);
	}
	
	public void logOut(Stage stage) {
		new LoginScreen(stage);
		AccountManager.getInstance().setCurrentUser(null, false);
		AccountManager.getInstance().saveState();
		AuctionManager.getInstance().saveState();
	}
	/**
	 * select all timed auctions using multithreading
	 */
	private void checkTimedAuctions() {
		Multithread<MenuScreen> observer = new Multithread<>(null, account -> {
			ArrayList<TimedAuction> check = new ArrayList<>();
			
			for(Auction a: AuctionManager.getInstance().getAuctions()) {
				if(a instanceof TimedAuction) check.add((TimedAuction) a);
			}
			for(TimedAuction t: check) {
				if(t.getDate().compareTo(LocalDate.now()) == 0) this.endAuction(t); continue;
			}
			return null;
		});
		observer.start();
	}
	/**
	 * check if transaction can be done and remove the given timed auction
	 */
	private void endAuction(TimedAuction auction) {
		if(auction.getBidder() != null) {
			User buyer = null, seller = null;
			
			for(Account a: AccountManager.getInstance().getAccounts()) {
				if(a instanceof User) {
					if(((User) a).getEmail().equals(auction.getBidder())) buyer = (User) a;
					if(((User) a).getEmail().equals(auction.getOwner())) seller = (User) a;
				}
			}
			try {
				if(auction.getMethod().equals(Constants.FIAT) && buyer.getFiatWallet().getFunds() - auction.getCurPrice() >= 0) {
					PaymentSystem.getInstance().handleTransaction(buyer, seller, auction.getCurPrice(), new FIATPayment());
				}
				else if(auction.getMethod().equals(Constants.CRYPTO) && buyer.getCryptoWallet().getFunds() - auction.getCryptoPrice() >= 0) {
					PaymentSystem.getInstance().handleTransaction(buyer, seller, auction.getCurPrice(), new CryptoPayment());
				}
			} catch (NotEnoughFundsException nefe) {}
		}
		this.removeReferences(auction);
		AuctionManager.getInstance().removeAuction(auction);
		AccountManager.getInstance().saveState();
	}
	/**
	 * removes all references to the given auction from all of the user profiles
	 */
	private void removeReferences(TimedAuction auction) {
		for(Account a: AccountManager.getInstance().getAccounts()) {
			if(a instanceof User) {
				if(((User) a).getFollowed().contains(auction.getID())) {
					((User) a).getFollowed().remove(Integer.valueOf(auction.getID()));
				}
				if(((User) a).getOwnAuctions().contains(auction.getID())) {
					((User) a).getOwnAuctions().remove(Integer.valueOf(auction.getID()));
				}
			}
		}
	}
}
