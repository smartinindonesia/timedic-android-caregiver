package com.smartin.timedic.caregiver;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.DigitalClock;
import android.widget.TextView;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.smartin.timedic.caregiver.model.utilitymodel.AlarmModel;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlarmActivity extends AppCompatActivity {
    public static final String TAG = "[AlarmActivity]";

    @BindView(R.id.swipe_btn)
    SwipeButton swipeButton;
    @BindView(R.id.digitalClock)
    DigitalClock digitalClock;
    @BindView(R.id.notifText)
    TextView notifText;

    private MediaPlayer mediaPlayer;
    private AlarmModel remind;
    private Vibrator v;
    private VibratePattern task;
    MyTaskParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        Log.i(TAG, "LEWAT INI");
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        startTask();
        mediaPlayer = MediaPlayer.create(this, R.raw.apple_ring);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);//set looping true to run it infinitely
        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                if (active) {
                    finish();
                }
            }
        });
        Bundle extras = getIntent().getExtras();
        updateUI(extras);
        setFonts();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //On destory stop and release the media player
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
        task.cancel(true);
    }

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(notifText);
        arrayList.add(digitalClock);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-ExtraBold.otf");
    }

    private void updateUI(Bundle extras){
        if(extras != null) {
            Log.i(TAG, "NOT NULL extras");
            if (extras.containsKey(AlarmModel.ACTION_MEDICINE_NAME)) {
                String medicineName = extras.getString(AlarmModel.ACTION_MEDICINE_NAME);
                Integer numOfMedicine = extras.getInt(AlarmModel.ACTION_NUM_OF_MEDICINE);
                Integer intervalTime = extras.getInt(AlarmModel.ACTION_INTERVAL_TIME);
                String medicineShape = extras.getString(AlarmModel.ACTION_MEDICINE_SHAPE);
                Integer intervalDay = extras.getInt(AlarmModel.ACTION_INTERVAL_DAY);
                notifText.setText(medicineName + " " + numOfMedicine + "x" + intervalTime + " " + medicineShape + "/" + intervalDay + " hari");
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        updateUI(extras);
    }

    private static class MyTaskParams {
        int dot, dash, gap;

        MyTaskParams (int dot, int dash, int gap) {
            this.dot = dot;
            this.dash = dash;
            this.gap = gap;
        }
    }

    private void startTask() {
        params = new MyTaskParams(200,500,200);
        task = new VibratePattern();
        task.execute(params);
    }

    public Integer onVibrate (Integer dot, Integer dash, Integer gap) {
        long[] pattern = {
                0,
                dot, gap, dash, gap, dot, gap, dot
        };

        v.vibrate(pattern, -1);
        int span = dot + gap + dash + gap + dot + gap + dot + gap;
        return span;
    }

    private class VibratePattern extends AsyncTask<MyTaskParams, Void, Integer> {

        @Override
        protected Integer doInBackground(MyTaskParams... params) {
            int span;
            span = onVibrate(params[0].dot,params[0].dash,params[0].gap);
            return span;
        }

        @Override
        protected void onPostExecute(Integer span) {
            final android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isCancelled()) {
                        startTask();
                    }
                }
            }, span);
        }
    }
}
