package com.example.rcovi.wake_me_app;

/**
 * Created by rcovi on 28/08/2017.
 */

import android.content.Intent;
import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.example.rcovi.wake_me_app.model.Player;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScoreScreenActivity extends AppCompatActivity {

    private String mPlayerString;
    private int mThemeId;
    private Ringtone ringtone;
    @BindView(R.id.roundsCompletedView)
    TextView mSuccessfulGuesses;
    @BindView(R.id.levelReachedView)
    TextView mLevelReached;
    @BindView(R.id.totalPointsView)
    TextView mTotalPointsView;
    @BindView(R.id.playAgainButtonView)
    Button mPlayAgainButton;
    @BindView(R.id.notEnough)
    TextView mNotEnough;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mThemeId = intent.getIntExtra("themeid", 0);
        mPlayerString = intent.getStringExtra("playerString");
        Uri ringtoneUri = Uri.parse(getIntent().getExtras().getString("ringtone-uri"));
        this.ringtone = RingtoneManager.getRingtone(this, ringtoneUri);

        Gson gS = new Gson();
        Player player = gS.fromJson(mPlayerString, Player.class);

        setTheme(mThemeId);
        setContentView(R.layout.activity_score_screen);
        ButterKnife.bind(this);

        String retry=("Your score is not enough, retry!");
        mSuccessfulGuesses.setText(Integer.toString(player.getSuccessfulGuesses()));
        mLevelReached.setText(Integer.toString(player.getLevel()));
        mTotalPointsView.setText(Integer.toString(player.getPlayerPoints()));
        mNotEnough.setText(retry);

        //Toast.makeText(getApplicationContext(), mPlayerString.toString(), Toast.LENGTH_LONG).show();
        mPlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ringtone.isPlaying()) ringtone.stop();
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Toast.makeText(getApplicationContext(), "Get more than 3000 points to dismiss alarm", Toast.LENGTH_LONG).show();
        ringtone.play();
    }

}
