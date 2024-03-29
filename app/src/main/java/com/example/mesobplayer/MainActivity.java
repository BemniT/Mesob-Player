package com.example.mesobplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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
        getSupportActionBar().hide();


        tvTime = findViewById(R.id.tvTime);
        tvDuration = findViewById(R.id.tvDuration);
        seekBarTime = findViewById(R.id.seekBarTime);
        seekBarVolume = findViewById(R.id.seekBarVolume);
        bntPlay = findViewById(R.id.btnPlay);

        musicPlayer = MediaPlayer.create(this, R.raw.free_sound);
        musicPlayer.setLooping(true);
        musicPlayer.seekTo(0);
        musicPlayer.setVolume(0.5f, 0.5f);

        bntPlay.setOnClickListener(this);




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