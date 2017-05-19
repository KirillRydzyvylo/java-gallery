package com.example.kirill.javagallery.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;



public class SettingsStore {

    private static final String STORE_SETTINGS = "storeSetting";
    private static final String KEY_DISPOSITION = "keyDisposition";

    private SharedPreferences settings;

    public SettingsStore( Context context) {
        settings = context.getSharedPreferences(STORE_SETTINGS, context.MODE_PRIVATE);
    }

    public void saveDisposition(int disposition){
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(KEY_DISPOSITION, disposition);
        editor.commit();
        Log.e("disposition", "запись temperature(SettingsStore) "+disposition);
    }

    public int getDisposition(){return settings.getInt(KEY_DISPOSITION, 0 ); }


}
