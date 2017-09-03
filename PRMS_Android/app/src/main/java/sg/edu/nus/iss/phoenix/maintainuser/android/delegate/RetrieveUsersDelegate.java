package sg.edu.nus.iss.phoenix.maintainuser.android.delegate;

import java.io.IOException;
import java.io.InputStream;
import java.lang.Object;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.maintainuser.android.controller.UserController;
import sg.edu.nus.iss.phoenix.core.android.delegate.*;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.phoenix.maintainuser.entity.*;

/**
 * @author jackle
 * @version 1.0
 * @created 01-Sep-2017 5:31:25 PM
 */
public class RetrieveUsersDelegate extends AsyncTask {
	// Tag for logging
	private static final String TAG = RetrieveUsersDelegate.class.getName();

	private UserController userController = null;

	public RetrieveUsersDelegate(UserController userController) {
		this.userController = userController;
	}

	@Override
	protected Object doInBackground(Object[] params) {
		Uri builtUri1 = Uri.parse(DelegateHelper.PRMS_BASE_URL_USER).buildUpon().build();
		Uri builtUri = Uri.withAppendedPath(builtUri1, params[0].toString()).buildUpon().build();
		Log.v(TAG, builtUri.toString());
		URL url = null;
		try {
			url = new URL(builtUri.toString());
		} catch (MalformedURLException e) {
			Log.v(TAG, e.getMessage());
			return e.getMessage();
		}

		String jsonResp = null;
		HttpURLConnection urlConnection = null;
		try {
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = urlConnection.getInputStream();

			Scanner scanner = new Scanner(in);
			scanner.useDelimiter("\\A");
			if (scanner.hasNext()) jsonResp = scanner.next();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) urlConnection.disconnect();
		}

		return jsonResp;
	}

	@Override
	protected void onPostExecute(Object result) {
		List<User> users = new ArrayList<>();

		if (result != null && !result.equals("")) {
			try {
				JSONObject reader = new JSONObject(result.toString());
				JSONArray rpArray = reader.getJSONArray("usrList");

				for (int i = 0; i < rpArray.length(); i++) {
					JSONObject rpJson = rpArray.getJSONObject(i);
					String id = rpJson.getString("id");
					String name = rpJson.getString("name");
					String password = rpJson.getString("password");
					JSONArray rolesArray = rpJson.getJSONArray("roles");
					ArrayList<Role> roles = new ArrayList<Role>();
					for (int j = 0; j < rolesArray.length(); j++) {
						JSONObject roleJson = rolesArray.getJSONObject(j);
						roles.add(new Role(roleJson.getString("role")));
					}

					users.add(new User(id, name, password, roles));
				}
			} catch (JSONException e) {
				Log.v(TAG, e.getMessage());
			}
		} else {
			Log.v(TAG, "JSON response error.");
		}

		if (userController != null)
			userController.userRetrieved(users);
	}
	public UserController m_UserController;

	public RetrieveUsersDelegate(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}
}//end RetrieveUsersDelegate