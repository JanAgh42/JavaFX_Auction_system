package application.auction;

import application.art.VisualArt;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class TargetPriceAuction extends Auction{
	
	private static final long serialVersionUID = -2014411255004749219L;
	private double targetPrice;
	private double cryptoPrice;

	public TargetPriceAuction(int auctionID, String name, VisualArt item, double price, double cryptoPrice, String owner) {
		super(auctionID, name, item, owner, cryptoPrice == -1 ? -1 : 0);

		this.targetPrice = price;
		this.cryptoPrice = cryptoPrice;
	}
	
	public double getPrice() {
		return this.targetPrice;
	}
	
	public double getCrTarget() {
		return this.cryptoPrice;
	}
	
	@Override
	public String toString() {
		return this.item + "   " 
		+ this.getName() + "   Target price: " 
		+ this.targetPrice + "€  " + (this.cryptoPrice == -1 ? "     " : this.cryptoPrice + " ETH     ") + "Current price: " 
		+ this.getCurPrice() + "€  " + (this.cryptoPrice == -1 ? "" : this.getCryptoPrice() + " ETH");
	}
}
