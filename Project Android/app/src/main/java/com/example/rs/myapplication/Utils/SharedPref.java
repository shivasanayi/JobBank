package com.example.rs.myapplication.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class SharedPref {
    Context mctx;
    private String UserId;
    private boolean IsLogin;


    public SharedPref(Context ctx) {
        this.mctx = ctx;
    }

    public String getUserId() {
        SharedPreferences sp = mctx.getSharedPreferences("PREFS", Activity.MODE_PRIVATE);
        UserId = sp.getString("UserId", "");
        return UserId;
    }

    public void setUserId(String UserId) {
        SharedPreferences settings = mctx.getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("UserId", UserId);
        editor.apply();

    }


    public boolean getIsLogin() {
        SharedPreferences sp = mctx.getSharedPreferences("PREFS", Activity.MODE_PRIVATE);
        IsLogin = sp.getBoolean("IsLogin", false);
        return IsLogin;
    }



    public void setIsLogin(boolean isLogin) {
        SharedPreferences settings = mctx.getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("IsLogin", isLogin);
        editor.apply();
    }




}
