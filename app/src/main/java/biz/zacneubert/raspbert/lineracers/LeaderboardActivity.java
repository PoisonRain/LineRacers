package biz.zacneubert.raspbert.lineracers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import biz.zacneubert.raspbert.lineracers.Music.Music;

/**
 * Created by Robert on 3/3/2016.
 */
public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_activity);

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
