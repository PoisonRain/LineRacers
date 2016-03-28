package biz.zacneubert.raspbert.lineracers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;

import biz.zacneubert.raspbert.lineracers.Music.Music;

/**
 * Created by josephk on 3/1/2016.
 */
public class PostGameActivity extends Activity {

    private Button MainMenuBtn;
    private Button PlayAgainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postgame_menu);

        MainMenuBtn = (Button) findViewById(R.id.main_menu);
        PlayAgainBtn = (Button) findViewById(R.id.play_again);

        MainMenuBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        Music.playSelectSound(v.getContext());
                        Intent intent = new Intent(PostGameActivity.this, MainMenuActivity.class);
                        PostGameActivity.this.startActivity(intent);
                        PostGameActivity.this.finish();
                    }
                }
        );
        PlayAgainBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        Music.playSelectSound(v.getContext());
                        Intent intent = new Intent(PostGameActivity.this, GameLobbyActivity.class);
                        PostGameActivity.this.startActivity(intent);
                        PostGameActivity.this.finish();
                    }
                }
        );
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
