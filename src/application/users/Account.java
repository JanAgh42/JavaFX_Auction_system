package application.users;

import java.io.Serializable;
import java.util.ArrayList;
import application.gui.Screen;
import application.managers.AccountManager;
import application.observer.Observer;
import application.observer.Subject;
import application.tools.Notification;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
abstract public class Account extends Subject<Screen> implements Serializable, Observer{

	private static final long serialVersionUID = -5108260067587009210L;
	private String name;
	private String email;
	private String password;
	protected ArrayList<Notification<?>> notifs;
	
	public Account(String name, String email, String password) {
		
		this.name = name;
		this.email = email;
		this.password = password;
		this.notifs = new ArrayList<>();
	}
	
	public ArrayList<Notification<?>> getNotifs(){
		return this.notifs;
	}
	/**
	 * deletes the given notification or message from the user's inbox
	 */
	public void removeNotif(Notification<?> notification) {
		this.notifs.remove(notification);
		this.update(null);
		AccountManager.getInstance().saveState();

	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getName() {
		return this.name;
	}
	/**
	 * adds the received Notification object to the user's list of notifs
	 */
	@Override
	public <S> void inform(S object) {
		if(object instanceof Notification<?>) this.notifs.add((Notification<?>) object);
	}
}
