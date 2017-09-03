package sg.edu.nus.iss.phoenix.maintainuser.android.ui;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.*;
import sg.edu.nus.iss.phoenix.maintainuser.entity.*;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.MaintainProgramScreen;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

import java.util.*;
/**
 * @author jackle
 * @version 1.0
 * @created 01-Sep-2017 5:30:59 PM
 */
public class MaintainUserScreen extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener{

	// Tag for logging
	private static final String TAG = MaintainProgramScreen.class.getName();

    private EditText mRPIdEditText;
	private EditText mRPNameEditText;
	private EditText mRPPasswordEditText;
	private Switch mStationManagerSwitch;
	private Switch mPresenterSwitch;
	private Switch mProducerSwitch;
	private Switch mSystemAdminSwitch;
	private User usredit = null;
    private ArrayList<String> roles;
	KeyListener mRPNameEditTextKeyListener = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

        roles = new ArrayList<String>();
		// Find all relevant views that we will need to read user input from
        mRPIdEditText = (EditText) findViewById(R.id.maintain_user_id_text_view);
		mRPNameEditText = (EditText) findViewById(R.id.maintain_user_name_text_view);
		mRPPasswordEditText = (EditText) findViewById(R.id.maintain_user_password_text_view);
		mStationManagerSwitch = (Switch) findViewById(R.id.switchStationManager);
		mPresenterSwitch = (Switch) findViewById(R.id.switchPresenter);
		mProducerSwitch = (Switch) findViewById(R.id.switchProducer);
		mSystemAdminSwitch = (Switch) findViewById(R.id.switchSystemAdminstrator);

        mStationManagerSwitch.setOnCheckedChangeListener(this);
        mPresenterSwitch.setOnCheckedChangeListener(this);
        mProducerSwitch.setOnCheckedChangeListener(this);
        mSystemAdminSwitch.setOnCheckedChangeListener(this);

		// Keep the KeyListener for name EditText so as to enable editing after disabling it.
		mRPNameEditTextKeyListener = mRPNameEditText.getKeyListener();
	}

	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		ControlFactory.getUserController().onDisplayUser(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu options from the res/menu/menu_editor.xml file.
		// This adds menu items to the app bar.
		getMenuInflater().inflate(R.menu.menu_editor, menu);
		return true;
	}

	/**
	 * This method is called after invalidateOptionsMenu(), so that the
	 * menu can be updated (some menu items can be hidden or made visible).
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		// If this is a new radioprogram, hide the "Delete" menu item.
		if (this.usredit == null) {
			MenuItem menuItem = menu.findItem(R.id.action_delete);
			menuItem.setVisible(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// User clicked on a menu option in the app bar overflow menu
		switch (item.getItemId()) {
			// Respond to a click on the "Save" menu option
			case R.id.action_save:
                selectSaveUser();
				return true;
			// Respond to a click on the "Delete" menu option
			case R.id.action_delete:
                selectDeleteUser();
				return true;
			// Respond to a click on the "Cancel" menu option
			case R.id.action_cancel:
				Log.v(TAG, "Canceling creating/editing user...");
				ControlFactory.getUserController().selectCancelCreateEditProgram();
				return true;
		}

		return true;
	}

	@Override
	public void onBackPressed() {
		Log.v(TAG, "Canceling creating/editing radio program...");
		ControlFactory.getUserController().selectCancelCreateEditProgram();
	}

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            if(!roles.contains(buttonView.getText().toString())) {
                roles.add(buttonView.getText().toString());
            }
        }

        if(!isChecked) {
            if(roles.contains(buttonView.getText().toString())) {
                roles.remove(buttonView.getText().toString());
            }
        }
    }

	public void createUser() {
		this.usredit = null;
        mRPIdEditText.setText("", TextView.BufferType.EDITABLE);
		mRPNameEditText.setText("", TextView.BufferType.EDITABLE);
		mRPPasswordEditText.setText("", TextView.BufferType.EDITABLE);
		mPresenterSwitch.setChecked(false);
        mStationManagerSwitch.setChecked(false);
        mSystemAdminSwitch.setChecked(false);
        mProducerSwitch.setChecked(false);
	}

	public void editUser(User rp2edit) {
		this.usredit = rp2edit;
		if (rp2edit != null) {
            mRPIdEditText.setText(rp2edit.getUserId(), TextView.BufferType.NORMAL);
			mRPNameEditText.setText(rp2edit.getUserName(), TextView.BufferType.NORMAL);
			mRPPasswordEditText.setText(rp2edit.getUserPassword(), TextView.BufferType.EDITABLE);
            ArrayList<Role> roles = rp2edit.getRoles();
            ArrayList<String> roleList = new ArrayList<String>();
			for(int i =0; i < roles.size(); i++){
				roleList.add(roles.get(i).getRole().toLowerCase());
			}

            mPresenterSwitch.setChecked(roleList.contains(mPresenterSwitch.getText().toString().toLowerCase()));
            mStationManagerSwitch.setChecked(roleList.contains(mStationManagerSwitch.getText().toString().toLowerCase()));
            mSystemAdminSwitch.setChecked(roleList.contains(mSystemAdminSwitch.getText().toString().toLowerCase()));
            mProducerSwitch.setChecked(roleList.contains(mProducerSwitch.getText().toString().toLowerCase()));
		}
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public void selectDeleteUser(){
        Log.v(TAG, "Deleting user " + usredit.getUserName() + "...");
        ControlFactory.getUserController().selectDeleteUser(usredit);
	}

	public void selectSaveUser(){
		ArrayList<Role> roleList = new ArrayList<Role>();
		for(int i = 0; i < roles.size(); i++) {
			roleList.add(new Role(roles.get(i).toLowerCase()));
		}
        // Save radio program.
        if (usredit == null) { // Newly created.
            Log.v(TAG, "Saving radio program " + mRPNameEditText.getText().toString() + "...");


            User usr = new User(this.mRPIdEditText.getText().toString(),
                    this.mRPNameEditText.getText().toString(), this.mRPPasswordEditText.getText().toString(), roleList);
            ControlFactory.getUserController().selectCreateUser(usr);
        }
        else { // Edited.
            Log.v(TAG, "Saving user " + usredit.getUserName() + "...");
            usredit.setUserName(mRPIdEditText.getText().toString());
            usredit.setUserName(mRPNameEditText.getText().toString());
            usredit.setUserPassword(mRPPasswordEditText.getText().toString());
            usredit.setUserRoles(roleList);
            ControlFactory.getUserController().selectModifyUser(usredit);
        }
	}
}//end MaintainUserScreen