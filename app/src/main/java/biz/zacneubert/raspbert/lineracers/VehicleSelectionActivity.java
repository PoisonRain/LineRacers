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
public class VehicleSelectionActivity extends AppCompatActivity {

    private Button useVehicleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_selection_activity);

        useVehicleBtn = (Button) findViewById(R.id.use_vehicle);

        useVehicleBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        Music.playSelectSound(v.getContext());
                        Intent intent = new Intent(VehicleSelectionActivity.this, CourseSelectionActivity.class);
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
