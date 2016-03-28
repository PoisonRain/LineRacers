package biz.zacneubert.raspbert.lineracers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;

import biz.zacneubert.raspbert.lineracers.Music.Music;

/**
 * Created by Adam on 2/29/16.
 */
public class CourseSelectionActivity extends AppCompatActivity {

    private Button selectCourseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_selection_activity);

        selectCourseBtn = (Button) findViewById(R.id.use_course);

        selectCourseBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        Music.playSelectSound(v.getContext());
                        Intent intent = new Intent(CourseSelectionActivity.this, GameLobbyActivity.class);
                        startActivity(intent);
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
