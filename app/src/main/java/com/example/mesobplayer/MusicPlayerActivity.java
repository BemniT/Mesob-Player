package com.example.mesobplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener{

    // views declaration
    TextView tvTime, tvDuration;
    SeekBar seekBarTime, seekBarVolume;
    Button bntPlay;

    MediaPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //hide the actionbar
//        getSupportActionBar().hide();


        tvTime = findViewById(R.id.tvTime);
        tvDuration = findViewById(R.id.tvDuration);
        seekBarTime = findViewById(R.id.seekBarTime);
        seekBarVolume = findViewById(R.id.seekBarVolume);
        bntPlay = findViewById(R.id.btnPlay);

        musicPlayer = MediaPlayer.create(this, R.raw.free_sound);
        musicPlayer.setLooping(true);
        musicPlayer.seekTo(0);
        musicPlayer.setVolume(0.5f, 0.5f);


        String duration = miliSecondsToString(musicPlayer.getDuration());
        tvDuration.setText(duration);
        bntPlay.setOnClickListener(this);

        seekBarTime.setMax(musicPlayer.getDuration());
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    musicPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (musicPlayer != null){
                    if (musicPlayer.isPlaying()){
                        try {
                            final double current = musicPlayer.getCurrentPosition();
                            final String elapsedTime = miliSecondsToString((int ) current);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvTime.setText(elapsedTime);
                                    seekBarTime.setProgress((int) current);
                                }
                            });


                            Thread.sleep(1000);
                        }catch (InterruptedException e){

                        }
                    }
                }
            }
        }).start();

        seekBarVolume.setProgress(50);
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = progress / 100f;
                musicPlayer.setVolume(volume, volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public String miliSecondsToString(int time)
    {
        String elapsedTime = "";
        int minutes = time/1000/ 60;
        int seconds = time/ 1000 % 60;
        elapsedTime = minutes+":";
        if (seconds < 10){
            elapsedTime += "0";
        }
        elapsedTime += seconds;

        return elapsedTime;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnPlay){
            if(musicPlayer.isPlaying()){
                // is playing
                musicPlayer.pause();
                bntPlay.setBackgroundResource(R.drawable.ic_play);
            }
            else {
                // on pause
                musicPlayer.start();
                bntPlay.setBackgroundResource(R.drawable.ic_pause);

            }
        }

    }
}