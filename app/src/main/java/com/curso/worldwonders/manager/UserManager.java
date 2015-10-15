package com.curso.worldwonders.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.curso.worldwonders.entity.User;
import com.curso.worldwonders.infrastructure.Constants;
import com.curso.worldwonders.infrastructure.OperationListener;
import com.curso.worldwonders.integrator.BackendIntegrator;
import com.curso.worldwonders.integrator.BackendOperationDescriptor;
import com.curso.worldwonders.integrator.OperationResult;
import com.curso.worldwonders.integrator.WorldWondersApi;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junior on 30/07/2015.
 */
public class UserManager {

    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public UserManager(Context context) {
        this.mContext = context;
    }

    public void register(final User user, final OperationListener<OperationResult<JSONObject>> callback) {

        new AsyncTask<Void, Integer, OperationResult<JSONObject>>() {

            @Override
            protected OperationResult<JSONObject> doInBackground(Void... params) {
                BackendOperationDescriptor descriptor = new BackendOperationDescriptor();
                descriptor.requestMethod = "POST";
                descriptor.endpoint = WorldWondersApi.Host + WorldWondersApi.Register;
                JSONObject userJson = new JSONObject();

                try {
                    userJson.put("username",user.email);
                    userJson.put("email", user.email);
                    userJson.put("password", user.password);
                    userJson.put("name", user.name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                descriptor.bodyParameters = userJson;
                BackendIntegrator integrator = new BackendIntegrator();
                OperationResult<JSONObject> result = integrator.executeOperation(descriptor);
                String token = result.result.optString("name");

                mSharedPreferences = mContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(Constants.SharedPrefsConsts.KEY_TOKEN, token);
                editor.apply();

                return result;
            }

            @Override
            protected void onPostExecute(OperationResult<JSONObject> result) {
                if (result!=null){
                    callback.onSuccess(result);
                }   else{
                    callback.onError(result.code);
                }
            }

        }.execute();

    }

    public void register(User user) {
        mSharedPreferences = mContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.SharedPrefsConsts.KEY_NAME, user.name);
        editor.putString(Constants.SharedPrefsConsts.KEY_EMAIL, user.email);
        editor.putString(Constants.SharedPrefsConsts.KEY_LANGUAGE, user.language);
        editor.putString(Constants.SharedPrefsConsts.KEY_PASSWORD, user.password);
        editor.apply();
    }

    public void login(final User user,  final OperationListener<OperationResult<JSONObject>> callback) {

        new AsyncTask<Void, Integer, OperationResult<JSONObject>>() {

            @Override
            protected OperationResult<JSONObject> doInBackground(Void... params) {

                try {
                    BackendOperationDescriptor descriptor = new BackendOperationDescriptor();
                    descriptor.requestMethod = "GET";
                    //String parameters = "username=" + user.email + "&password=" + user.password;

                    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("username",user.email));
                    parameters.add(new BasicNameValuePair("password",user.password));

                    String formatedParameters = URLEncodedUtils.format(parameters,"UTF-8");

                    descriptor.endpoint = WorldWondersApi.Host + WorldWondersApi.Login + formatedParameters;

                    BackendIntegrator integrator = new BackendIntegrator();
                    OperationResult<JSONObject> result = integrator.executeOperation(descriptor);
                    String token = result.result.optString("name");

                    mSharedPreferences = mContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(Constants.SharedPrefsConsts.KEY_TOKEN, token);
                    editor.apply();

                    return result;

                } catch (Exception e) {
                    Crashlytics.log(Log.ERROR,"WorldWonders","Crash ao fazer login");
                }

                return null;

            }

            @Override
            protected void onPostExecute(OperationResult<JSONObject> result) {
                if (result!=null){
                    callback.onSuccess(result);
                }   else{
                    callback.onError(result.code);
                }
            }

        }.execute();

    }


    public boolean login(User user) {
        mSharedPreferences = mContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userEmail = mSharedPreferences.getString(Constants.SharedPrefsConsts.KEY_TOKEN, null);
        String userPassword = mSharedPreferences.getString(Constants.SharedPrefsConsts.KEY_PASSWORD, null);

        if (userEmail.equals(user.email) && userPassword.equals(user.password)) {
            mSharedPreferences = mContext.getSharedPreferences("logged_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(Constants.SharedPrefsConsts.KEY_LOGGED, true);
            editor.putString(Constants.SharedPrefsConsts.KEY_EMAIL, user.email);
            editor.apply();
            return true;
        } else {
            return false;
        }

    }

    public void logout() {
        mSharedPreferences = mContext.getSharedPreferences("logged_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
