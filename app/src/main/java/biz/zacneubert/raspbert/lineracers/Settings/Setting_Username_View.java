package biz.zacneubert.raspbert.lineracers.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import biz.zacneubert.raspbert.lineracers.R;

/**
 * Created by josephk on 3/2/2016.
 */
public class Setting_Username_View {
    View rootView;
    EditText txtUsername;

    Context c;

    public Setting_Username_View(LayoutInflater inflater, Context c) {
        rootView = inflater.inflate(R.layout.setting_username_view, null, false);
        this.c = c;

        final Context context = c;
        txtUsername = (EditText) rootView.findViewById(R.id.setting_username_edit);
        txtUsername.setText(Setting_Username.getSavedValue(c));
        txtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(Setting_Username.getKey(), s.toString());
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
