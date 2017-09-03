package sg.edu.nus.iss.phoenix.maintainuser.android.delegate;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Object;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sg.edu.nus.iss.phoenix.maintainuser.android.controller.UserController;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.phoenix.maintainuser.entity.*;
import sg.edu.nus.iss.phoenix.maintainuser.android.controller.UserController;
import sg.edu.nus.iss.phoenix.maintainuser.android.delegate.CreateUserDelegate;
import sg.edu.nus.iss.phoenix.maintainuser.entity.User;

import java.util.*;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER;
//import phoenix.user.service.UserService;
//import phoenix.user.restful.UserRESTService;

/**
 * @author jackle
 * @version 1.0
 * @created 01-Sep-2017 5:31:23 PM
 */
public class CreateUserDelegate extends AsyncTask {

	// Tag for logging
	private static final String TAG = CreateUserDelegate.class.getName();

	private final UserController userController;

	public CreateUserDelegate(UserController UserController) {
		this.userController = UserController;
	}

	@Override
	protected Boolean doInBackground(Object... params) {
		Uri builtUri = Uri.parse(PRMS_BASE_URL_USER).buildUpon().build();
		builtUri = Uri.withAppendedPath(builtUri,"create").buildUpon().build();
		Log.v(TAG, builtUri.toString());
		URL url = null;
		try {
			url = new URL(builtUri.toString());
		} catch (MalformedURLException e) {
			Log.v(TAG, e.getMessage());
			return new Boolean(false);
		}

		JSONObject json = new JSONObject();
		try {
			User user = (User) params[0];
			json.put("id", user.getUserId());
			json.put("name", user.getUserName());
			json.put("password", user.getUserPassword());
			JSONArray roles = new JSONArray();
			ArrayList<Role> roleList = user.getRoles();
			for (int i=0;i<roleList.size();i++)
			{
				JSONObject jObjd=new JSONObject();
				jObjd.put("role", roleList.get(i).getRole());
				roles.put(jObjd);
			}

			json.put("roles", roles);
		} catch (JSONException e) {
			Log.v(TAG, e.getMessage());
		}

		boolean success = false;
		HttpURLConnection httpURLConnection = null;
		DataOutputStream dos = null;
		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setInstanceFollowRedirects(false);
			httpURLConnection.setRequestMethod("PUT");
			httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			dos = new DataOutputStream(httpURLConnection.getOutputStream());
			dos.writeUTF(json.toString());
			dos.write(256);
			Log.v(TAG, "Http PUT response " + httpURLConnection.getResponseCode());
			success = true;
		} catch (IOException exception) {
			Log.v(TAG, exception.getMessage());
		} finally {
			if (dos != null) {
				try {
					dos.flush();
					dos.close();
				} catch (IOException exception) {
					Log.v(TAG, exception.getMessage());
				}
			}
			if (httpURLConnection != null) httpURLConnection.disconnect();
		}
		return new Boolean(success);
	}

	@Override
	protected void onPostExecute(Object result) {
		userController.userCreated((boolean)result);
	}
}//end CreateUserDelegate