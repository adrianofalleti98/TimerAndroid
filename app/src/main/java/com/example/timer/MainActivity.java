package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    public TextView timerView;
    public SeekBar seekbar;
    public Button start;
    public Button reset;
    public long duration;

    public long durationLeft;
    public int start_stop;
    public CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerView = findViewById(R.id.timerView);
        seekbar = findViewById(R.id.seekBar);
        start = findViewById(R.id.start_button);
        reset = findViewById(R.id.reset_button);
        duration = 0;
        durationLeft = duration;
        start_stop = 0;
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            System.out.println(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            System.out.println(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                duration = TimeUnit.MINUTES.toMillis(seekbar.getProgress());
                timer = new CountDownTimer(duration,1000){

                    @Override
                    public void onTick(long l) {
                        durationLeft = l;
                        String sDuration =  String.format(Locale.ENGLISH,"%02d : %02d : %02d",TimeUnit.MILLISECONDS.toHours(l),TimeUnit.MILLISECONDS.toMinutes(l)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)),TimeUnit.MILLISECONDS.toSeconds(l)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                        timerView.setText(sDuration);
                    }

                    @Override
                    public void onFinish() {
                        //timerView.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Countdown ended",Toast.LENGTH_LONG).show();
                        seekbar.setEnabled(true);
                    }
                };
                timerView.setText(String.format("%02d : %02d : %02d", TimeUnit.MILLISECONDS.toHours(duration),TimeUnit.MILLISECONDS.toMinutes(duration)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),TimeUnit.MILLISECONDS.toSeconds(duration)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))));
            }
        });



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekbar.setEnabled(false);
                if(start_stop == 0) {
                    timer.start();
                    start.setText("Stop");
                    start_stop = 1;
                }
                else if(start_stop == 1)
                {
                    timer.cancel();
                    timer = new CountDownTimer(durationLeft,1000) {
                        @Override
                        public void onTick(long l) {
                            durationLeft = l;
                            String sDuration =  String.format(Locale.ENGLISH,"%02d : %02d : %02d",TimeUnit.MILLISECONDS.toHours(l),TimeUnit.MILLISECONDS.toMinutes(l)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)),TimeUnit.MILLISECONDS.toSeconds(l)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                            timerView.setText(sDuration);
                        }

                        @Override
                        public void onFinish() {
                            Toast.makeText(getApplicationContext(),"Countdown ended",Toast.LENGTH_LONG).show();
                            seekbar.setEnabled(true);
                        }
                    };
                   start.setText("Start");
                   start_stop = 0;

                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekbar.setProgress(0);
                seekbar.setEnabled(true);
                start.setText("Start");
                timer.cancel();
                timerView.setText("00:00:00");
            }
        });

    }
}