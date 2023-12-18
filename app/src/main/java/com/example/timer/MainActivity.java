package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    public Integer start_stop;
    public CountDownTimerWithPause timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = new CountDownTimerWithPause(duration,1000,timerView,start,seekbar,getApplicationContext());
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
                duration = TimeUnit.MINUTES.toMillis(seekbar.getProgress());
                timer.setDuration(duration);
                timerView.setText(String.format("%02d : %02d : %02d", TimeUnit.MILLISECONDS.toHours(duration),TimeUnit.MILLISECONDS.toMinutes(duration)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),TimeUnit.MILLISECONDS.toSeconds(duration)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                duration = TimeUnit.MINUTES.toMillis(seekbar.getProgress());
                timer = new CountDownTimerWithPause(duration,1000,timerView,start,seekbar,getApplicationContext());

            }
        });



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(timer.getStart_stop() == 0) {
                    if (timer.getDuration() == 0){
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Attenzione il timer è settato a 0");
                        builder.setTitle("ERROR");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                seekbar.setProgress(1);
                                duration = TimeUnit.MINUTES.toMillis(1);
                                timerView.setText(String.format("%02d : %02d : %02d",
                                        TimeUnit.MILLISECONDS.toHours(duration),
                                        TimeUnit.MILLISECONDS.toMinutes(duration)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),
                                        TimeUnit.MILLISECONDS.toSeconds(duration)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))));
                                timer = new CountDownTimerWithPause(duration,1000,timerView,start,seekbar,getApplicationContext());


                            }
                        }
                        );
                        builder.create().show();
                    }
                    else {
                    seekbar.setEnabled(false);
                    timer.start();
                    start.setText("Stop");
                    start_stop = 1;
                    timer.setStart_stop(start_stop);

                }
                }
                else if(timer.getStart_stop() == 1)
                {
                    timer.cancel();
                    long dl = timer.getDurationLeft();
                    timer = new CountDownTimerWithPause(dl,1000,timerView,start,seekbar,getApplicationContext());
                    //creando un timer da zero, start_stop viene settata a 0, quindi non c'è bisogno di settarla qui a 0, come facciamo nell'altro ramo if in cui la settiamo a uno
                    start.setText("Start");
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
                duration = 0;
                start_stop = 0;
                timer.setDuration(duration);
                timer.setStart_stop(start_stop);
                timerView.setText("00:00:00");
            }
        });

    }
}