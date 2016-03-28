package biz.zacneubert.raspbert.lineracers.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by zacneubert on 2/3/16.
 */
public class Setting_Jump_Forward_Time extends Setting {
    public static String getSavedValue(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        String savedValue = sp.getString(Setting_Jump_Forward_Time.getKey(), "10");
        return savedValue;
    }

    public static int getJumpTime(Context c) {
        return Integer.parseInt(getSavedValue(c));
    }

    public static String getKey() {
        return "JUMP_FORWARD_TIME";
    }

    @Override
    public View getView(Context c) {
        LayoutInflater inflater = LayoutInflater.from(c);
        return new Setting_Jump_Forward_Time_View(inflater, c).getView();
    }
}
