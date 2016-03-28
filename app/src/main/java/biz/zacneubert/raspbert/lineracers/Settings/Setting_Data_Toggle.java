package biz.zacneubert.raspbert.lineracers.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by zacneubert on 1/27/16.
 */
public class Setting_Data_Toggle extends Setting {
    public static String getSavedValue(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        String savedValue = sp.getString(Setting_Data_Toggle.getKey(), "ENABLED");
        return savedValue;
    }

    public static String getKey() {
        return "DATA_TOGGLE";
    }

    public static Boolean dataDisabled(Context c) {
        return Setting_Data_Toggle.getSavedValue(c).equals("DISABLED");
    }

    @Override
    public View getView(Context c) {
        LayoutInflater inflater = LayoutInflater.from(c);
        return new Setting_Data_Toggle_View(inflater, c).getView();
    }
}
