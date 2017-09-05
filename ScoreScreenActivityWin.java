package com.example.rcovi.wake_me_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.example.rcovi.wake_me_app.model.Player;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScoreScreenActivityWin extends AppCompatActivity {

    private String mPlayerString;
    private int mThemeId;
    @BindView(R.id.roundsCompletedView)
    TextView mSuccessfulGuesses;
    @BindView(R.id.levelReachedView)
    TextView mLevelReached;
    @BindView(R.id.totalPointsView)
    TextView mTotalPointsView;
    @BindView(R.id.playAgainButtonView)
    Button mPlayAgainButton;
    @BindView(R.id.exitButtonn)
    Button mExitButton;
    Intent toFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mThemeId = intent.getIntExtra("themeid", 0);
        mPlayerString = intent.getStringExtra("playerString");

        Gson gS = new Gson();
        Player player = gS.fromJson(mPlayerString, Player.class);

        setTheme(mThemeId);
        setContentView(R.layout.activity_score_screen_win);
        ButterKnife.bind(this);

        mSuccessfulGuesses.setText(Integer.toString(player.getSuccessfulGuesses()));
        mLevelReached.setText(Integer.toString(player.getLevel()));
        mTotalPointsView.setText(Integer.toString(player.getPlayerPoints()));

        mPlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mExitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            final AlertDialog dialog = new AlertDialog.Builder(ScoreScreenActivityWin.this).create();
        dialog.setTitle("Thank You for using Wake Me App! Have a nice day!");
        dialog.setMessage("Would you like to exit now?");
        dialog.setButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                    dialog.dismiss();
                }
            });
        dialog.show();
        }
} );
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        final AlertDialog dialog = new AlertDialog.Builder(ScoreScreenActivityWin.this).create();
        dialog.setTitle("Thank You for using Wake Me App! Have a nice day!");
        dialog.setMessage("Would you like to exit now?");
        toFirst = new Intent(this,First.class);
        dialog.setButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(toFirst);
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
