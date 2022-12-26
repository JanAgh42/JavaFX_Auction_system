package application.auction;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import application.art.VisualArt;
import application.observer.Subject;
import application.tools.Constants;
import application.tools.Notification;
import application.users.User;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class Auction extends Subject<User> implements Serializable{
	
	private static final long serialVersionUID = 775092657404771983L;
	protected int auctionID;
	protected double currentPrice;
	protected double currentCryptoPrice;
	protected String name;
	protected VisualArt item;
	protected String owner;
	
	public Auction(int auctionID, String name, VisualArt item, String owner, double crypto) {
		this.owner = owner;
		this.currentPrice = 0;
		this.currentCryptoPrice = crypto;
		this.item = item;
		this.name = name;
		this.auctionID = auctionID;
	}

	public int getID() {
		return this.auctionID;
	}
	
	public double getCurPrice() {
		return this.currentPrice;
	}
	
	public double getCryptoPrice() {
		return this.currentCryptoPrice;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public VisualArt getItemType() {
		return this.item;
	}
	/**
	 * update the current price of a given auction + send a notification to every subscriber using observer
	 */
	public void setCurrentPrice(double currentPrice, double cryptoPrice) {
		String message = "The current price of this item has been increased to: " + currentPrice + " €";
		String time = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
		
		this.currentPrice = currentPrice;
		this.currentCryptoPrice = cryptoPrice;
		
		this.update(new Notification<Auction>(message, time, this, auction -> {
			return (auction instanceof TimedAuction ? Constants.TIMED : Constants.TARGET) + "    " + auction.getItemType() + "    " + auction.getName();
		}));
	}
}
