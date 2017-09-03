package sg.edu.nus.iss.phoenix.maintainuser.entity;


/**
 * This object represents the access privileges of a role that is assigned to
 * users.
 * @created 03-Sep-2017 9:24:40 PM
 */
public class Role {

	/**
	 * The access privileges for the user role. 
	 */
	private String accessPrivilege;
	/**
	 * The name of the user role. 
	 */
	private String role;

	public Role(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * The constructor of the user role.
	 * 
	 * @param role    The name of the user role.
	 */
	public Role(String role){
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}//end Role