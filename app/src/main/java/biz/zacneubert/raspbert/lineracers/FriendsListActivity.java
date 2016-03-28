package biz.zacneubert.raspbert.lineracers;

import android.support.v7.app.AppCompatActivity;

import biz.zacneubert.raspbert.lineracers.Music.Music;

/**
 * Created by Adam on 2/29/16.
 */
public class FriendsListActivity extends AppCompatActivity {


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
