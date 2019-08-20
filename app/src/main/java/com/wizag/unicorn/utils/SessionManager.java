package com.wizag.unicorn.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.wizag.unicorn.ui.activities.Activity_Login;

import java.util.HashMap;


public class SessionManager {
    // Shared Preferences
    SharedPreferences prefs;


    // Editor for Shared prefserences
    Editor editor;

    // Context
    Context _context;

    // Shared prefs mode
    int PRIVATE_MODE = 0;

    // Sharedprefs file name
    private static final String PREF_NAME = "Wizag";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String FIRST_NAME = "fName";
    public static final String LAST_NAME = "lName";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String TOKEN = "token";

    public static final String ACCOUNT_STATUS = "status";


    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        prefs = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }


    public void savePassword(String password) {
        editor.putString(TOKEN, password);
        editor.commit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String token, String fName, String lName,String email,String phone) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in prefs
        editor.putString(FIRST_NAME, fName);
        editor.putString(LAST_NAME, lName);
        editor.putString(EMAIL, email);
        editor.putString(PHONE, phone);
        editor.putString(TOKEN, token);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Activity_Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(FIRST_NAME, prefs.getString(FIRST_NAME , null));
        user.put(LAST_NAME, prefs.getString(LAST_NAME , null));
        user.put(EMAIL, prefs.getString(EMAIL , null));
        user.put(PHONE, prefs.getString(PHONE , null));
        user.put(TOKEN, prefs.getString(TOKEN, null));
        user.put(ACCOUNT_STATUS, prefs.getString(ACCOUNT_STATUS, null));


        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Activity_Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);

    }

    /**
     * Quick check for login
     **/
    // Get Login Model_Category
    public boolean isLoggedIn() {
        return prefs.getBoolean(IS_LOGIN, false);
    }
}