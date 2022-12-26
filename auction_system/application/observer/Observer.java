package application.observer;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public interface Observer {

	public <S> void inform(S object);
}
