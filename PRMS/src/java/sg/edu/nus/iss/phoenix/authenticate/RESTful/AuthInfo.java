/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.authenticate.RESTful;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/**
 *
 * @author User
 */
public class AuthInfo {
   private String username;
    private boolean authStatus;
    private String role;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    

    public boolean isAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(boolean authStatus) {
        this.authStatus = authStatus;
    }

    

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
  
}
