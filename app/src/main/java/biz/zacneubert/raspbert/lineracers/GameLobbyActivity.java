package biz.zacneubert.raspbert.lineracers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;

import biz.zacneubert.raspbert.lineracers.Music.Music;

/**
 * Created by Adam on 3/1/16.
 */
public class GameLobbyActivity extends AppCompatActivity {

    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_lobby_activity);

        startBtn = (Button) findViewById(R.id.startGame);

        startBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        Music.playSelectSound(v.getContext());
                        Intent intent = new Intent(GameLobbyActivity.this, PostGameActivity.class); //This will be changed later to start game and ending the game will call PostGameActivity
                        startActivity(intent);
                        GameLobbyActivity.this.finish();
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
