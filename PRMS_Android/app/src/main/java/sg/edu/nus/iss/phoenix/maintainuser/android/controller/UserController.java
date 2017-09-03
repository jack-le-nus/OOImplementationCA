package sg.edu.nus.iss.phoenix.maintainuser.android.controller;

import android.content.Intent;
import android.util.Log;

import sg.edu.nus.iss.phoenix.maintainuser.android.ui.MaintainUserScreen;
import sg.edu.nus.iss.phoenix.maintainuser.entity.User;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.maintainuser.android.delegate.*;
import sg.edu.nus.iss.phoenix.maintainuser.android.ui.*;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.CreateProgramDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.DeleteProgramDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.RetrieveProgramsDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.MaintainProgramScreen;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.ProgramListScreen;

import java.util.*;

/**
 * @author jackle
 * @version 1.0
 * @created 01-Sep-2017 5:31:01 PM
 */
public class UserController {

	private UserListScreen m_userListScreen;
	private MaintainUserScreen m_maintainProgramScreen;
	private User m_User;

	public UserController(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param screen
	 */
	public void onDisplayUser(MaintainUserScreen screen){
		this.m_maintainProgramScreen = screen;
		if (m_User == null)
			m_maintainProgramScreen.createUser();
		else
			m_maintainProgramScreen.editUser(m_User);
	}

	/**
	 * 
	 * @param screen
	 */
	public void onDisplayUserList(UserListScreen screen){
		this.m_userListScreen = screen;
		new RetrieveUsersDelegate(this).execute("all");
	}

	public void selectCreateUser(){
		m_User = null;
		Intent intent = new Intent(MainController.getApp(), MaintainUserScreen.class);
		MainController.displayScreen(intent);
	}

	public void selectEditUser(User user) {
		m_User = user;
//		Log.v(TAG, "Editing radio program: " + radioProgram.getRadioProgramName() + "...");

		Intent intent = new Intent(MainController.getApp(), MaintainUserScreen.class);
		MainController.displayScreen(intent);
	}

	public void selectCreateUser(User user){
		new CreateUserDelegate(this).execute(user);
	}

	public void selectModifyUser(User user){
		new EditUserDelegate(this).execute(user);
	}

	public void selectDeleteUser(User user){
		new DeleteUserDelegate(this).execute(user.getUserId());
	}

	public void selectCancelCreateEditProgram() {
		startUseCase();
	}

	public void startUseCase(){
		m_User = null;
		Intent intent = new Intent(MainController.getApp(), UserListScreen.class);
		MainController.displayScreen(intent);
	}

	/**
	 * 
	 * @param success
	 */
	public void userCreated(boolean success){
		startUseCase();
	}

	public void userDeleted(boolean success){
		startUseCase();
	}

	public void userUpdated(boolean success){
		startUseCase();
	}

	/**
	 * 
	 * @param users
	 */
	public void userRetrieved(List<User> users){
		m_userListScreen.showUsers(users);
	}
}//end UserController