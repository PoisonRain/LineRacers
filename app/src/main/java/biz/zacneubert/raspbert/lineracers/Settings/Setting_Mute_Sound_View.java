package biz.zacneubert.raspbert.lineracers.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import biz.zacneubert.raspbert.lineracers.Music.Music;
import biz.zacneubert.raspbert.lineracers.R;

/**
 * Created by zacneubert on 1/27/16.
 */
public class Setting_Mute_Sound_View implements CompoundButton.OnCheckedChangeListener {
    View rootView;
    CheckBox MuteSoundCheckBox;

    Context c;

    public Setting_Mute_Sound_View(LayoutInflater inflater, Context c) {
        rootView = inflater.inflate(R.layout.setting_mute_sound_layout, null, false);

        this.c = c;

        MuteSoundCheckBox = (CheckBox) rootView.findViewById(R.id.checkboxMuteSound);
        if(Setting_Mute_Sound.soundMuted(c)) {
            MuteSoundCheckBox.setChecked(true);
        }
        MuteSoundCheckBox.setOnCheckedChangeListener(this);
    }
    public View getView() {
        return rootView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        buttonView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sp.edit();
        String value = "DISABLED";
        if(!isChecked) {  //Sound is not disabled
            value = "ENABLED";
        }
        editor.putString(Setting_Mute_Sound.getKey(), value);
        editor.commit();

        if(!isChecked) {  //Must be after commit for play to work
            Music.startBackgroundMusic(rootView.getContext());
        }
        else {
            Music.pauseBackgroundMusic();
        }
    }
}
