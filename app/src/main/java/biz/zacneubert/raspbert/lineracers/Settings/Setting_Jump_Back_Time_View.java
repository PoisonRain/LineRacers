package biz.zacneubert.raspbert.lineracers.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import biz.zacneubert.raspbert.lineracers.R;

/**
 * Created by zacneubert on 2/3/16.
 */
public class Setting_Jump_Back_Time_View {
    View rootView;
    EditText txtJumpBack;

    Context c;

    public Setting_Jump_Back_Time_View(LayoutInflater inflater, Context c) {
        rootView = inflater.inflate(R.layout.setting_jump_back_time_layout, null, false);
        this.c = c;

        final Context context = c;
        txtJumpBack = (EditText) rootView.findViewById(R.id.setting_jump_back_time_edit);
        txtJumpBack.setText(Setting_Jump_Back_Time.getSavedValue(c));
        txtJumpBack.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(Setting_Jump_Back_Time.getKey(), s.toString());
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public View getView() {
        return rootView;
    }
}
