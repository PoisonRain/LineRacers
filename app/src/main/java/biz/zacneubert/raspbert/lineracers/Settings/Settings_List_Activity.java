package biz.zacneubert.raspbert.lineracers.Settings;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import biz.zacneubert.raspbert.lineracers.Music.Music;
import biz.zacneubert.raspbert.lineracers.R;

/**
 * Created by zacneubert on 11/23/15.
 */
public class Settings_List_Activity extends AppCompatActivity {
    ListView Settings_List;
    List<Setting> Settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_list_layout);

        Settings = new ArrayList<>();
        //ADD ALL SETTINGS HERE!!!
        //ADD ALL SETTINGS HERE!!!
//        Settings.add(new Setting_Theme());
        Settings.add(new Setting_Music_Choice());
        Settings.add(new Setting_Mute_Sound());
        Settings.add(new Setting_Username());

        Settings_List = (ListView) this.findViewById(R.id.settingsList);
        Settings_List.setAdapter(new Settings_List_Adapter(this, R.layout.setting_list_row_dummy, Settings));
    }

    public class Settings_List_Adapter extends ArrayAdapter<Setting> {
        public List<Setting> adapter_settings;

        public Settings_List_Adapter(Context context, int resource, List<Setting> objects) {
            super(context, resource, objects);

            adapter_settings = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Setting s = adapter_settings.get(position);
            return s.getView(getContext());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Music.pauseBackgroundMusic();
    }

    @Override
    public void onResume() {
        super.onResume();
        Music.startBackgroundMusic(this);
    }
}
