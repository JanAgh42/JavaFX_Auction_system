package application.tools;

import application.controllers.Outcome;
import application.gui.Screen;
import javafx.application.Platform;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class Multithread<T extends Screen> extends Thread{
	
	private Outcome<T> option;
	private T object;

	public Multithread(T object, Outcome<T> option) {
		this.option = option;
		this.object = object;
	}
	/**
	 * executes the given lambda in a new thread (both background and GUI manipulations)
	 */
	@Override
	public void run() {	
		if(object == null) this.option.outcome(this.object);
		else {
			Platform.runLater(() -> {
				this.option.outcome(this.object);
			});
		}
	}
}
