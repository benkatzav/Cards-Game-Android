package com.example.cardgame2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WinnerActivity extends AppCompatActivity {
    private ImageView winnerLeft;
    private ImageView winnerRight;
    private ImageView draw;
    private TextView leftScore;
    private TextView rightScore;
    private ImageView playAgain;
    private TextView playAgainTV;
    private TextView PlayerOneName;
    private TextView PlayerTwoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {

            setContentView(R.layout.activity_main);
        }

        winnerLeft = findViewById(R.id.winner_IMG_winnerLeft);
        winnerRight = findViewById(R.id.winner_IMG_winnerRight);
        rightScore = findViewById(R.id.game_LBL_scoreRight);
        leftScore = findViewById(R.id.game_LBL_scoreLeft);
        PlayerOneName = findViewById(R.id.winner_LBL_firstPlayerName);
        PlayerTwoName = findViewById(R.id.winner_LBL_secondPlayerName);
        draw = findViewById(R.id.winner_IMG_draw);


        winnerRight.setVisibility(View.INVISIBLE);
        winnerLeft.setVisibility(View.INVISIBLE);
        draw.setVisibility(View.INVISIBLE);

        PlayerOneName.setText(getIntent().getExtras().getString("PLAYER_ONE_NAME"));
        PlayerTwoName.setText(getIntent().getExtras().getString("PLAYER_TWO_NAME"));

        String left_score = getIntent().getStringExtra("LEFT_SCORE");
        String right_score = getIntent().getStringExtra("RIGHT_SCORE");
        rightScore.setText(right_score);
        leftScore.setText(left_score);
        if (Integer.valueOf(right_score) > Integer.valueOf(left_score))
            winnerRight.setVisibility(View.VISIBLE);
        else if (Integer.valueOf(right_score) < Integer.valueOf(left_score))
            winnerLeft.setVisibility(View.VISIBLE);
        else
            draw.setVisibility(View.VISIBLE);

        playAgain = findViewById(R.id.game_BTN_playAgain);
        playAgain.setOnClickListener(onClickListener);
        playAgainTV = findViewById(R.id.game_TV_playAgain);
        playAgainTV.setOnClickListener(onClickListener);

    }


    @Override
    protected void onStart() {
        Log.d("lifeCheck", "WinnerActivityOnStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("lifeCheck", "WinnerActivityOnResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d("lifeCheck", "WinnerActivityOnStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("lifeCheck", "WinnerActivityOnDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d("lifeCheck", "WinnerActivityOnPause");
        super.onPause();
    }
    private void openActivity(Class activity) {
        Intent myIntent = new Intent(WinnerActivity.this, activity);
        myIntent.putExtra("PlayerTwoScore", getIntent().getStringExtra("RIGHT_SCORE"));
        myIntent.putExtra("PlayerOneScore", getIntent().getStringExtra("LEFT_SCORE"));
        myIntent.putExtra("PlayerOneName", getIntent().getExtras().getString("PLAYER_ONE_NAME"));
        myIntent.putExtra("PlayerTwoName", getIntent().getExtras().getString("PLAYER_TWO_NAME"));
        myIntent.putExtra("KEY",1);
        startActivity(myIntent);
        finish();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case(R.id.game_BTN_playAgain):
                case(R.id.game_TV_playAgain):
                    openActivity(RecordsActivity.class);
                    break;
            }
        }
    };

}