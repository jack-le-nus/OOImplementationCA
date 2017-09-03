package sg.edu.nus.iss.phoenix.maintainuser.service;

import sg.edu.nus.iss.phoenix.maintainuser.service.*;
import java.sql.SQLException;
import java.util.ArrayList;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.authenticate.dao.*;
import sg.edu.nus.iss.phoenix.authenticate.entity.*;

public class UserService {
	DAOFactoryImpl factory;
	UserDao usrdao;

	public UserService() {
		super();
		// Sorry. This implementation is wrong. To be fixed.
		factory = new DAOFactoryImpl();
		usrdao = factory.getUserDAO();
	}
	public ArrayList<User> searchUsers(User usrso) {
		ArrayList<User> list = new ArrayList<User>();
		try {
			list = (ArrayList<User>) usrdao.searchMatching(usrso);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<User> findUSRByCriteria(User usr) {
		ArrayList<User> currentList = new ArrayList<User>();

		try {
			currentList = (ArrayList<User>) usrdao.searchMatching(usr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return currentList;

	}

	public User findUSR(String usrName) {
		User currentusr = new User();
		currentusr.setName(usrName);
		try {
			currentusr = ((ArrayList<User>) usrdao
					.searchMatching(currentusr)).get(0);
			return currentusr;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentusr;

	}

	public ArrayList<User> findAllUSR() {
		ArrayList<User> currentList = new ArrayList<User>();
		try {
			currentList = (ArrayList<User>) usrdao.loadAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentList;

	}
        
	public void processCreate(User usr) {
		try {
			usrdao.create(usr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void processModify(User usr) {
		
			try {
				usrdao.save(usr);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void processDelete(String id) {

            try {
                User usr = new User(id);
                usrdao.delete(usr);
            } catch (NotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	}

}
