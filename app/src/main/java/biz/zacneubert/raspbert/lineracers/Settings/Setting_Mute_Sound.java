package biz.zacneubert.raspbert.lineracers.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by zacneubert on 3/2/16.
 */
public class Setting_Mute_Sound extends Setting {
    public static String getSavedValue(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        String savedValue = sp.getString(Setting_Mute_Sound.getKey(), "ENABLED");
        return savedValue;
    }

    public static String getKey() {
        return "SETTING_MUTE_SOUND";
    }

    public static Boolean soundMuted(Context c) {
        return Setting_Mute_Sound.getSavedValue(c).equals("DISABLED");
    }

    @Override
    public View getView(Context c) {
        LayoutInflater inflater = LayoutInflater.from(c);
        return new Setting_Mute_Sound_View(inflater, c).getView();
    }
}
