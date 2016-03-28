package biz.zacneubert.raspbert.lineracers.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by josephk on 3/2/2016.
 */
public class Setting_Username extends Setting {
    public static String getSavedValue(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        String usernameValue = sp.getString(Setting_Username.getKey(), "Player 1");
        return usernameValue;
    }

    public static String getUsername(Context c) {
        return getSavedValue(c);
    }

    public static String getKey() {
        return "SETTING_USERNAME_KEY";
    }

    @Override
    public View getView(Context c) {
        LayoutInflater inflater = LayoutInflater.from(c);
        return new Setting_Username_View(inflater, c).getView();
    }
}
