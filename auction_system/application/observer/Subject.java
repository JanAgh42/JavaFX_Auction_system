package application.observer;

import java.util.ArrayList;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class Subject<T extends Observer> {
	
	private ArrayList<T> listOfObservers;
	
	public Subject() {
		this.listOfObservers = new ArrayList<>();
	}
	/**
	 * adds a new observer to the list of observers
	 */
	public void subscribe(T subscriber) {
		this.listOfObservers.add(subscriber);
	}
	/**
	 * deletes a given observer from the list of observers
	 */
	public void unsubscribe(T subscriber) {
		this.listOfObservers.remove(subscriber);
	}
	/**
	 * checks if the list of observers contains a specific entry
	 */
	public boolean findSubscriber(T subscriber) {
		if(this.listOfObservers.contains(subscriber)) return true;
		else return false;
	}
	/**
	 * calls the inform method in every observing object
	 */
	public <S> void update(S object) {
		for(T observer: this.listOfObservers) {
			observer.inform(object);
		}
	}
}
