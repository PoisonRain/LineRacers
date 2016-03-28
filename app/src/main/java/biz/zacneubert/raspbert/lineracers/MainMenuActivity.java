package biz.zacneubert.raspbert.lineracers;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import biz.zacneubert.raspbert.lineracers.Music.Music;
import biz.zacneubert.raspbert.lineracers.Settings.Settings_List_Activity;

public class MainMenuActivity extends Activity {

    private Button playBtn;
    private Button settingBtn;

    private Button leaderboardBtn;
    private Button helpBtn;

    private Button shittyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        playBtn = (Button) findViewById(R.id.single);
        settingBtn = (Button) findViewById(R.id.settings2);

        leaderboardBtn = (Button) findViewById(R.id.leaderboard);
        helpBtn = (Button) findViewById(R.id.help);

        ObjectAnimator Title = ObjectAnimator.ofFloat(findViewById(R.id.boat), "x", 2500);
        Title.setDuration(7000);
        Title.setRepeatCount(ValueAnimator.INFINITE);
        //Title.setRepeatMode(ValueAnimator.REVERSE);
        Title.start();

        playBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        Music.playSelectSound(v.getContext());
                        Intent intent = new Intent(MainMenuActivity.this, VehicleSelectionActivity.class);
                        startActivity(intent);
                    }
                }
        );
        settingBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        Music.playSelectSound(v.getContext());
                        Intent intent = new Intent(MainMenuActivity.this, Settings_List_Activity.class);
                        startActivity(intent);
                    }
                }
        );

        leaderboardBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        Music.playSelectSound(v.getContext());
                        Intent intent = new Intent(MainMenuActivity.this, LeaderboardActivity.class);
                        startActivity(intent);
                    }
                }
        );
        helpBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        Music.playSelectSound(v.getContext());
                        Intent intent = new Intent(MainMenuActivity.this, HelpActivity.class);
                        startActivity(intent);
                    }
                }
        );

        shittyButton = (Button) findViewById(R.id.startGameplayActivityButton);
        shittyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Music.playSelectSound(v.getContext());
                Intent intent = new Intent(MainMenuActivity.this, GameplayActivity.class);
                startActivity(intent);
            }
        });

        Music.PlayBackgroundMusic(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
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
