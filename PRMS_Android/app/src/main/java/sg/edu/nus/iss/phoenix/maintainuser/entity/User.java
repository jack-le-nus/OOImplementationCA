package sg.edu.nus.iss.phoenix.maintainuser.entity;


import java.util.ArrayList;

/**
 * This entity object represents the common user characteristics.
 * @updated 01-Sep-2017 5:31:49 PM
 */
public class User {

	/**
	 * The user id of the user. 
	 */
	private String id;
	/**
	 * The name of the user. 
	 */
	private String name;
	/**
	 * The password of the user. 
	 */
	private String password;
	/**
	 * The names of all roles assigned to the user. 
	 */
	private ArrayList<Role> roles;

	public User(String id, String name, String password, ArrayList<Role> roles){
		this.id = id;
		this.name = name;
		this.password = password;
		this.roles = roles;
	}

	public void finalize() throws Throwable {

	}

	public String getUserId() {
		return id;
	}

	public String getUserName() {
		return name;
	}

	public String getUserPassword() {
		return password;
	}

	public ArrayList<Role> getRoles() {
		return roles;
	}

	public void setUserId(String id) {
		this.id = id;
	}

	public void setUserName(String name) {
		this.name = name;
	}

	public void setUserPassword(String password) {
		this.password = password;
	}

	public void setUserRoles(ArrayList<Role> roles) {
		this.roles = roles;
	}
}//end User