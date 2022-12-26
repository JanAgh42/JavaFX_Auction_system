package application.auction;

import java.time.LocalDate;

import application.art.VisualArt;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class TimedAuction extends Auction{
	
	private static final long serialVersionUID = -5848586852670441132L;
	private LocalDate date;
	private String highestBidder;
	private String payMethod;

	public TimedAuction(int auctionID, String name, VisualArt item, LocalDate date, boolean crypto, String owner) {
		super(auctionID, name, item, owner, crypto ? 0 : -1);
		
		this.highestBidder = null;
		this.payMethod = null;
		this.date = date;
	}
	
	public void setMethod(String method) {
		this.payMethod = method;
	}
	
	public void setBidder(String bidder) {
		this.highestBidder = bidder;
	}
	
	public String getBidder() {
		return this.highestBidder;
	}
	
	public String getMethod() {
		return this.payMethod;
	}
	
	public LocalDate getDate() {
		return this.date;
	}

	@Override
	public String toString() {
		return this.item + "   " 
		+ this.getName() + "   Expiration: " 
		+ this.date + "    Current price: " 
		+ this.getCurPrice() + "€  " + (this.getCryptoPrice() == -1 ? "" : this.getCryptoPrice() + " ETH");
	}
}
