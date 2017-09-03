/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.maintainuser.restful;

import sg.edu.nus.iss.phoenix.radioprogram.restful.*;
import java.util.List;
import sg.edu.nus.iss.phoenix.authenticate.entity.*;

/**
 *
 * @author User
 */
public class Users {
    
    private List <User> usrList;

    public List<User> getUsrList() {
        return usrList;
    }
 
    public void setUsrList(List<User> usrList) {
        this.usrList = usrList;
    }
    
}
