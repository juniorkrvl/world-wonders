package com.curso.worldwonders.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.curso.worldwonders.entity.User;
import com.curso.worldwonders.infrastructure.Constants;

/**
 * Created by Junior on 30/07/2015.
 */
public class UserManager {

    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public UserManager(Context context) {
        this.mContext = context;
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

    public boolean login(User user) {
        mSharedPreferences = mContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userEmail = mSharedPreferences.getString(Constants.SharedPrefsConsts.KEY_EMAIL, null);
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
