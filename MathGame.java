package com.example.rcovi.wake_me_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcovi.wake_me_app.model.ThemeWheel;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by rcovi on 26/08/2017.
 */

public class MathGame extends First {
   public static final String TAG = MathGame.class.getSimpleName();

    private String mPlayerName;
    private ThemeWheel mThemeWheel = new ThemeWheel();
    private int mThemeId = mThemeWheel.getThemeId();
    public String titleSong;

    @BindView(R.id.playButtonView) TextView mPlayButtonView;
    //@BindView(R.id.nameView) EditText mNameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(mThemeId);
        setContentView(R.layout.activity_mainmate);
        ButterKnife.bind(this);

        titleSong=getIntent().getExtras().getString("ringtone-uri");

        Intent xy=new Intent(MathGame.this,AlarmReceiver.class);
        xy.putExtra("Extra","alarm off");
        if(checkbok.isChecked()){
            xy.putExtra("vib","yes");
            //Toast.makeText(getApplicationContext(), "checked hai", Toast.LENGTH_LONG).show();
        }
        else
            xy.putExtra("vib","no");
        xy.putExtra("play", "-1");
        sendBroadcast(xy);


        mPlayButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayerName="Player1";
                startGame(mPlayerName, mThemeId);
            }
        });

    }

    private void startGame(String name, int themeId) {
        Intent intent = new Intent(this, GameActivity.class);
       // intent.putExtra("name", name);
        intent.putExtra("themeid", themeId);
        intent.putExtra("ringtone-uri",titleSong);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

}
