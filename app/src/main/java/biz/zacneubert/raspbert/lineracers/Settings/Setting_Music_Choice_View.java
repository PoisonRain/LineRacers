package biz.zacneubert.raspbert.lineracers.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import biz.zacneubert.raspbert.lineracers.Music.Music;
import biz.zacneubert.raspbert.lineracers.R;

/**
 * Created by zacneubert on 3/2/16.
 */
public class Setting_Music_Choice_View implements AdapterView.OnItemSelectedListener {
    Spinner Music_Choice_Spinner;
    View rootView;
    Context c;

    public Setting_Music_Choice_View(LayoutInflater inflater, Context c) {
        rootView = inflater.inflate(R.layout.setting_music_choice_layout, null, false);
        this.c = c;

        Music_Choice_Spinner = (Spinner) rootView.findViewById(R.id.setting_music_choice_spinner);
        ArrayAdapter<CharSequence> adapter
                = ArrayAdapter.createFromResource(c, R.array.MUSIC_CHOICES, R.layout.simple_spinner_item);
        Music_Choice_Spinner.setAdapter(adapter);
        Music_Choice_Spinner.setSelection(
                adapter.getPosition(
                        new Setting_Music_Choice().getSavedValue(c)
                )
        );

        Music_Choice_Spinner.setOnItemSelectedListener(this);
    }

    public View getView() {
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        rootView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        String sortType = Music_Choice_Spinner.getSelectedItem().toString();
        //sortType = (String) parent.getItemAtPosition(position);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(new Setting_Music_Choice().getKey(), sortType);
        editor.commit();

        Music.BackgroundMusicChanged(Setting_Music_Choice.getMusicId(rootView.getContext()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //ALMOST LIKE I'M CODING NOTHING AT ALL (nothing at all, nothing at all)
    }
}
