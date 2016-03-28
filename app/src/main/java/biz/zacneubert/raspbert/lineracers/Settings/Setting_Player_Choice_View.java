package biz.zacneubert.raspbert.lineracers.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import biz.zacneubert.raspbert.lineracers.R;

/**
 * Created by zacneubert on 1/27/16.
 */
public class Setting_Player_Choice_View implements CompoundButton.OnCheckedChangeListener {
    View rootView;
    CheckBox playerChoiceCheckbox;

    Context c;

    public Setting_Player_Choice_View(LayoutInflater inflater, Context c) {
        rootView = inflater.inflate(R.layout.setting_player_choice_layout, null, false);

        this.c = c;

        playerChoiceCheckbox = (CheckBox) rootView.findViewById(R.id.checkboxPlayerChoice);
        if(!Setting_Player_Choice.useDefault(c)) {
            playerChoiceCheckbox.setChecked(true);
        }
        playerChoiceCheckbox.setOnCheckedChangeListener(this);
    }
    public View getView() {
        return rootView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        buttonView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sp.edit();
        String value = "3RDPARTY";
        if(!isChecked) {  //Data is not disabled
            value = "DEFAULT";
        }
        editor.putString(Setting_Player_Choice.getKey(), value);
        editor.commit();
    }
}
