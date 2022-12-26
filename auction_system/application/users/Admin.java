package application.users;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class Admin extends Account{

	private static final long serialVersionUID = 7735734216034781835L;

	public Admin(String name, String email, String password) {
		super(name, email, password);
	}
}
