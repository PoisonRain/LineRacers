package biz.zacneubert.raspbert.lineracers.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by zacneubert on 1/27/16.
 */
public class Setting_Player_Choice extends Setting {
    public static String getSavedValue(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        String savedValue = sp.getString(Setting_Player_Choice.getKey(), "DEFAULT");
        return savedValue;
    }

    public static String getKey() {
        return "PLAYER_CHOICE";
    }

    public static Boolean useDefault(Context c) {
        String SavedValue = Setting_Player_Choice.getSavedValue(c);
        return SavedValue.equals("DEFAULT");
    }

    @Override
    public View getView(Context c) {
        LayoutInflater inflater = LayoutInflater.from(c);
        return new Setting_Player_Choice_View(inflater, c).getView();
    }
}