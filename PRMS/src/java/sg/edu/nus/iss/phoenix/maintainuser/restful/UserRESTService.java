/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.maintainuser.restful;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import sg.edu.nus.iss.phoenix.authenticate.entity.*;
import sg.edu.nus.iss.phoenix.maintainuser.service.UserService;

/**
 * REST Web Service
 *
 * @author User
 */
@Path("maintainuser")
@RequestScoped
public class UserRESTService {

    @Context
    private UriInfo context;
    
    private UserService service;

    /**
     * Creates a new instance of UserRESTService
     */
    public UserRESTService() {
        service = new UserService();
    }

    /**
     * Retrieves representation of an instance of resource
     * @return an instance of resource
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Users getAllUsers() {
        ArrayList<User> usrlist = service.findAllUSR();
        Users usrsList = new Users();
        usrsList.setUsrList(new ArrayList<User>());
        
        for (int i = 0; i < usrlist.size(); i++) {
            User user = new User(usrlist.get(i).getId());
            user.setName(usrlist.get(i).getName());
            user.setPassword(usrlist.get(i).getPassword());
            user.setRoles(usrlist.get(i).getRoles());
            usrsList.getUsrList().add(user);
        }

        return usrsList;
    }
    
    /**
     * PUT method for updating or creating an instance of resource
     * @param content representation for the resource
     */
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(User usr) {
        service.processModify(usr);
    }
    
    /**
     * POST method for creating an instance of resource
     * @param content representation for the resource
     */
    @PUT
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createUser(User usr) {
        service.processCreate(usr);
    }
   
    /**
     * DELETE method for deleting an instance of resource
     * @param name name of the resource
     */
    @DELETE
    @Path("/delete/{usrname}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(@PathParam("usrname") String name) {
        String name2;
        try { 
            name2 = URLDecoder.decode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace(); 
            return;
        }

        service.processDelete(name2);
    }
}
