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
public class Setting_Data_Toggle_View implements CompoundButton.OnCheckedChangeListener {
    View rootView;
    CheckBox DisableDataCheckbox;

    Context c;

    public Setting_Data_Toggle_View(LayoutInflater inflater, Context c) {
        rootView = inflater.inflate(R.layout.setting_data_toggle_layout, null, false);

        this.c = c;

        DisableDataCheckbox = (CheckBox) rootView.findViewById(R.id.checkboxDataToggle);
        if(Setting_Data_Toggle.dataDisabled(c)) {
            DisableDataCheckbox.setChecked(true);
        }
        DisableDataCheckbox.setOnCheckedChangeListener(this);
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
        if(!isChecked) {  //Data is not disabled
            value = "ENABLED";
        }
        editor.putString(Setting_Data_Toggle.getKey(), value);
        editor.commit();
    }
}
