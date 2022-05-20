package application.managers;

import java.io.IOException;
import java.util.ArrayList;

import application.auction.Auction;
import application.gui.Screen;
import application.observer.Subject;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class AuctionManager extends Subject<Screen> implements Manager{

	private static AuctionManager auctions;
	private ArrayList<Auction> existingAuctions;
	
	private String path = "./auctions.ser";
	private StateSaver<ArrayList<Auction>> saver = null;
	
	private AuctionManager() {
		this.loadState();
	}
	/**
	 * Singleton class - returns the existing instance
	 */
	public static AuctionManager getInstance() {
		if(auctions == null) auctions = new AuctionManager();
		return auctions;
	}
	
	public ArrayList<Auction> getAuctions(){
		return this.existingAuctions;
	}
	/**
	 * adds a new auction object to the list and serializes everything 
	 */
	public void addNewAuction(Auction auction) {
		this.existingAuctions.add(auction);
		this.saveState();
	}
	/**
	 * removes the given auction from the list and serializes everything
	 */
	public void removeAuction(Auction auction) {
		this.existingAuctions.remove(auction);
		this.update(null);
		this.saveState();
	}
	/**
	 * saves all auctions using serialization
	 */
	public void saveState() {
		this.saver = new StateSaver<>(this.path, this.existingAuctions);
		
		try {
			this.saver.serialize();
			this.saver.closeWriteStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * loads all auctions from the data files using serialization
	 */
	public void loadState() {
		this.saver = new StateSaver<>(this.path, this.existingAuctions);
		
		try {
			this.existingAuctions = this.saver.deserialize();
			this.saver.closeReadStream();
		} catch (ClassNotFoundException | IOException e) {
			this.existingAuctions = new ArrayList<>();
		}
	}
}
