package com.salva.lecture.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedData {
    private static String PREFS_NAME = "LectureRes";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedData(Context context) {
       this.preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
       this.editor = this.preferences.edit();
    }

    public void setAccessToken(String token) {
        this.editor.putString("token", token).apply();

    }

    public void setUserId(int userId) {
        this.editor.putInt("userId", userId).apply();

    }

    public String getAccessToken(){
        return this.preferences.getString("token", null);
    }

    public int getUserId(){
        return this.preferences.getInt("userId", 0);
    }
}
