package com.example.parkig_reservation;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {

    private static final String PREF_NAME = "login_pref";
    private static final String KEY_USERID = "userid";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    // Constructor
    public MyPreferences(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Store the login status and userid
    public void setLoggedIn(boolean isLoggedIn, String userid) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.putString(KEY_USERID, userid);
        editor.apply();
    }

    // Check if the user is logged in
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Store the user ID
    public void setUserId(String userId) {
        editor.putString(KEY_USERID, userId);
        editor.apply();
    }


    // Get the logged-in userid
    public String getUserid() {
        return pref.getString(KEY_USERID, "");
    }



    // Clear login status and userid (on logout)
    public void clearLoggedInUser() {
        editor.clear();
        editor.apply();
    }
}
