package com.example.rcovi.wake_me_app;

/**
 * Created by rcovi on 03/09/2017.
 */
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import at.markushi.ui.CircleButton;


public class DodgeGame extends First implements GameCallBack {

    private GameView gameView;
    private RelativeLayout overlay;
    private TextView score;
    private TextView highScore;
    private MediaPlayer backgroundMusic;
    private static boolean sound = true;
    public String titleSong;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleSong=getIntent().getExtras().getString("ringtone-uri");
        Intent xy=new Intent(DodgeGame.this,AlarmReceiver.class);
        xy.putExtra("Extra","alarm off");
        if(checkbok.isChecked()){
            xy.putExtra("vib","yes");
            //Toast.makeText(getApplicationContext(), "checked hai", Toast.LENGTH_LONG).show();
        }
        else
            xy.putExtra("vib","no");
        xy.putExtra("play", "-1");
        sendBroadcast(xy);


        setContentView(R.layout.activity_main_dodge);
        gameView = (GameView) findViewById(R.id.surfaceView1);
        overlay = (RelativeLayout) findViewById(R.id.overlay);
        gameView.setGameCallback(this);
        score = (TextView) findViewById(R.id.score);
        highScore = (TextView) findViewById(R.id.best);
        highScore.setText(String.format(getString(R.string.score), getBestScore()));
        playBackgroundMusic();

    }


    public void onStart(View v) {
        playClick();
        removeOverlay();
        gameView.setStartState();
    }

    public void onSound(View v) {
        playClick();
        if (sound) {
            ((CircleButton) v).setImageResource(R.drawable.ic_volume_off_white_36dp);
            sound = false;
            backgroundMusic.pause();
        } else {
            ((CircleButton) v).setImageResource(R.drawable.ic_volume_up_white_36dp);
            sound = true;
            backgroundMusic.start();
        }

    }


    private void removeOverlay() {
        overlay.setVisibility(View.GONE);
    }

    @Override
    public void onGameOver(final long endTime, final boolean isDeath) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                overlay.setVisibility(View.VISIBLE);
                if (isDeath) {
                    playDeath();
                }
                setBestScore(endTime);
                score.setText(String.format(getString(R.string.score), endTime));
                highScore.setText(String.format(getString(R.string.highscore), getBestScore()));
            }
        });
    }

    @Override
    public void onBonusTouched() {
        playBonus();
    }

    @Override
    public void onSecondLife() {
        //TODO play sound here
        playSecondLife();
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        gameView.startSecondLife();
                    }
                }, 1000);

            }
        });

    }


    private void setBestScore(long endTime) {
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        long highScore = sharedPref.getLong(getString(R.string.saved_high_score), 0);
        if (highScore < endTime) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong(getString(R.string.saved_high_score), endTime);
            editor.apply();
        }
    }

    private long getBestScore() {
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getLong(getString(R.string.saved_high_score), 0);
    }

    // <-----SOUND----->

    private void playBackgroundMusic() {
        backgroundMusic = MediaPlayer.create(this, R.raw.background);
        backgroundMusic.setVolume(0.5f, 0.5f);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
    }

    private void playSecondLife() {
        MediaPlayer secondMusic = MediaPlayer.create(this, R.raw.hit);
        secondMusic.start();
    }

    private void playClick() {
        MediaPlayer clickMusic = MediaPlayer.create(this, R.raw.click);
        clickMusic.start();
    }

    private void playBonus() {
        MediaPlayer clickMusic = MediaPlayer.create(this, R.raw.bam);
        clickMusic.start();
    }

    private void playDeath() {
        MediaPlayer deathMusic = MediaPlayer.create(this, R.raw.die);
        deathMusic.start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            gameView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusic.pause();
        gameView.setGameOverState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("sound", sound);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        sound = savedInstanceState.getBoolean("sound");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO fix this - if sound is muted.
        if (sound) {
            backgroundMusic.start();
        }
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //   Toast.makeText(getApplicationContext(), "Solve the problem to dismiss alarm", Toast.LENGTH_LONG).show();
        Intent toStart = new Intent(DodgeGame.this, Startup.class);
        toStart.putExtra("ringtone-uri",titleSong);
        startActivity(toStart);
    }
}
