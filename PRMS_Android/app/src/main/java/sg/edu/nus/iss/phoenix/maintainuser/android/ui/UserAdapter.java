package sg.edu.nus.iss.phoenix.maintainuser.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.maintainuser.entity.*;

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(@NonNull Context context, ArrayList<User> Users) {
        super(context, 0, Users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_user, parent, false);
        }
        //    Word currentWord = getItem(position);
        User currentRP = getItem(position);

        EditText radioId = (EditText)listItemView.findViewById(R.id.maintain_user_id_text_view);
        radioId.setText(currentRP.getUserId(), TextView.BufferType.NORMAL);
        radioId.setKeyListener(null); // This disables editing.

        EditText radioName = (EditText)listItemView.findViewById(R.id.maintain_user_name_text_view);
        radioName.setText(currentRP.getUserName(), TextView.BufferType.NORMAL);
        radioName.setKeyListener(null);

        EditText radioPassword = (EditText)listItemView.findViewById(R.id.maintain_user_password_text_view);
        radioPassword.setVisibility(View.GONE);

        Switch mStationManagerSwitch = (Switch) listItemView.findViewById(R.id.switchStationManager);
        Switch mPresenterSwitch = (Switch) listItemView.findViewById(R.id.switchPresenter);
        Switch mProducerSwitch = (Switch) listItemView.findViewById(R.id.switchProducer);
        Switch mSystemAdminSwitch = (Switch) listItemView.findViewById(R.id.switchSystemAdminstrator);
        mStationManagerSwitch.setClickable(false);
        mPresenterSwitch.setClickable(false);
        mProducerSwitch.setClickable(false);
        mSystemAdminSwitch.setClickable(false);

        ArrayList<Role> roles = currentRP.getRoles();
        ArrayList<String> roleList = new ArrayList<String>();
        for(int i =0; i < roles.size(); i++){
            roleList.add(roles.get(i).getRole());
        }

        mPresenterSwitch.setChecked(roleList.contains(mPresenterSwitch.getText().toString().toLowerCase()));
        mStationManagerSwitch.setChecked(roleList.contains(mStationManagerSwitch.getText().toString().toLowerCase()));
        mSystemAdminSwitch.setChecked(roleList.contains(mSystemAdminSwitch.getText().toString().toLowerCase()));
        mProducerSwitch.setChecked(roleList.contains(mProducerSwitch.getText().toString().toLowerCase()));

        return listItemView;
    }
}
