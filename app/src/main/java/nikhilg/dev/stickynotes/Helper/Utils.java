package nikhilg.dev.stickynotes.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import nikhilg.dev.stickynotes.R;

/**
 * Created by nik on 29/4/16.
 */
public class Utils {

    public static Utils utils = null;
    private Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public static Utils getInstance(Context context) {
        if (utils == null) {
            utils = new Utils(context);
        }
        return utils;
    }

    public void addBoolValue(String key, boolean val) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, val);
        editor.commit();
    }

    public boolean getBoolValue(String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }

    public void addIntValue(String key, int val) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, val);
        editor.commit();
    }

    public int getIntValue(String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getInt(key, -1);
    }

    public void addValue(String key, String val) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public String getValue(String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }
}
