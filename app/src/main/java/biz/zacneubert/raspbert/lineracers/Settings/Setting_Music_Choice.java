package biz.zacneubert.raspbert.lineracers.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import biz.zacneubert.raspbert.lineracers.R;

/**
 * Created by zacneubert on 3/2/16.
 */
public class Setting_Music_Choice extends Setting {
    public static String getSavedValue(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getString(Setting_Music_Choice.getKey(), "CHILL");
    }

    private static Map<String, Integer> musicMap;
    public static int getMusicId(Context context) {
        if(musicMap == null) {
            musicMap = new HashMap<>();
            musicMap.put("CHILL", R.raw.chill);
            musicMap.put("ACID", R.raw.acid);
            musicMap.put("INSPIRE", R.raw.inspire);
            musicMap.put("BASS", R.raw.shortbass);
            musicMap.put("OLDIES", R.raw.oldies);
        }
        int musicID = musicMap.get(getSavedValue(context));
        return musicID;
    }

    public static String getKey() {
        return "SETTING_MUSIC_CHOICE";
    }

    @Override
    public View getView(Context c) {
        LayoutInflater inflater = LayoutInflater.from(c);
        return new Setting_Music_Choice_View(inflater, c).getView();
    }
}
