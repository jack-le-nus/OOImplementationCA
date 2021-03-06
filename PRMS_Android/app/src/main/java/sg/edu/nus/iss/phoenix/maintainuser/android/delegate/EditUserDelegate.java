package sg.edu.nus.iss.phoenix.maintainuser.android.delegate;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.maintainuser.android.controller.UserController;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sg.edu.nus.iss.phoenix.maintainuser.entity.Role;
import sg.edu.nus.iss.phoenix.maintainuser.entity.User;
import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ProgramController;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER;

/**
 * @author jackle
 * @version 1.0
 * @created 01-Sep-2017 5:31:28 PM
 */
public class EditUserDelegate extends AsyncTask<User, Void, Boolean> {

	// Tag for logging
	private static final String TAG = EditUserDelegate.class.getName();

	private final UserController userController;

	public EditUserDelegate(UserController userController) {
		this.userController = userController;
	}

	@Override
	protected Boolean doInBackground(User... params) {
		Uri builtUri = Uri.parse(PRMS_BASE_URL_USER).buildUpon().build();
		builtUri = Uri.withAppendedPath(builtUri,"update").buildUpon().build();
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
			json.put("id", params[0].getUserId());
			json.put("name", params[0].getUserName());
			json.put("password", params[0].getUserPassword());
			JSONArray roles = new JSONArray();
			ArrayList<Role> roleList = params[0].getRoles();
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
			httpURLConnection.setDoInput(true);
			httpURLConnection.setInstanceFollowRedirects(false);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
			httpURLConnection.setDoOutput(true);
			dos = new DataOutputStream(httpURLConnection.getOutputStream());
			dos.writeUTF(json.toString());
			dos.write(512);
			Log.v(TAG, "Http POST response " + httpURLConnection.getResponseCode());
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
	protected void onPostExecute(Boolean result) {
		userController.userUpdated(result.booleanValue());
	}
}//end EditUserDelegate