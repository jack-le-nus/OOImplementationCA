package sg.edu.nus.iss.phoenix.maintainuser.android.ui;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.maintainuser.entity.*;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.ProgramListScreen;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.RadioProgramAdapter;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jackle
 * @version 1.0
 * @created 01-Sep-2017 5:30:58 PM
 */
public class UserListScreen extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = UserListScreen.class.getName();

    // private EditText mRPNameEditText;
    // private EditText mRPDescEditText;
    // private EditText mDurationEditText;
    private ListView mListView;
    private UserAdapter mRPAdapter;
    private User selectedUsr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // mRPNameEditText = (EditText) findViewById(R.id.maintain_program_name_text_view);
        // mRPDescEditText = (EditText) findViewById(R.id.maintain_program_desc_text_view);
        // mDurationEditText = (EditText) findViewById(R.id.maintain_program_duration_text_view);

        ArrayList<User> users = new ArrayList<User>();
        mRPAdapter = new UserAdapter(this, users);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlFactory.getUserController().selectCreateUser();
            }
        });

        mListView = (ListView) findViewById(R.id.user_pm_list);
        mListView.setAdapter(mRPAdapter);

        // Setup the item selection listener
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Log.v(TAG, "Radio program at position " + position + " selected.");
                User rp = (User) adapterView.getItemAtPosition(position);
                // Log.v(TAG, "Radio program name is " + rp.getRadioProgramName());
                selectedUsr = rp;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // your stuff
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(0);

        ControlFactory.getUserController().onDisplayUserList(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "View" menu option
            case R.id.action_view:
                selectViewUser();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        ControlFactory.getProgramController().maintainedProgram();
    }

	public UserListScreen(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public void selectCreateUser(){

	}

	public void selectViewUser(){
        if (selectedUsr == null) {
            // Prompt for the selection of a radio program.
            Toast.makeText(this, "Select a user first! Use arrow keys on emulator", Toast.LENGTH_SHORT).show();
            Log.v(TAG, "There is no selected user.");
        }
        else {
            Log.v(TAG, "Viewing user: " + selectedUsr.getUserName() + "...");
            ControlFactory.getUserController().selectEditUser(selectedUsr);
        }
	}

	/**
	 * 
	 * @param users
	 */
	public void showUsers(List<User> users){
        mRPAdapter.clear();
        for (int i = 0; i < users.size(); i++) {
            mRPAdapter.add(users.get(i));
        }
	}
}//end UserListScreen