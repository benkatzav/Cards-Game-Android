package com.example.cardgame2;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GameActivity extends Activity {
    static String TAG = "GameActivity";
    private int intervalTime = 1500;
    private ProgressBar progressBar;
    private CardsDeck cardsDeck;
    private ImageView play;
    private TextView rightScore;
    private TextView leftScore;
    private ImageView leftCardImg;
    private ImageView rightCardImg;
    private int rightPlayerScore = 0;
    private int leftPlayerScore = 0;
    private int draws = 0;
    private TextView firstPlayerName;
    private TextView secondPlayerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("lifeCheck", "MainActivityOnCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.game_BTN_play);
        play.setOnClickListener(onClickListener);
        rightScore = findViewById(R.id.game_LBL_scoreRight);
        leftScore = findViewById(R.id.game_LBL_scoreLeft);
        leftCardImg = findViewById(R.id.game_IMG_card_1);
        rightCardImg = findViewById(R.id.game_IMG_card_2);
        progressBar = findViewById(R.id.game_PB_progressBar);
        firstPlayerName = findViewById(R.id.game_LBL_firstPlayerName);
        secondPlayerName = findViewById(R.id.game_LBL_secondPlayerName);

        firstPlayerName.setText(getIntent().getExtras().getString("Value1"));
        secondPlayerName.setText(getIntent().getExtras().getString("Value2"));

        cardsDeck = new CardsDeck();
        cardsDeck.shuffle();
        cardsDeck.split();

    }

    @Override
    protected void onStart() {
        Log.d("lifeCheck", "MainActivityOnStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("lifeCheck", "MainActivityOnResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d("lifeCheck", "MainActivityOnStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("lifeCheck", "MainActivityOnDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d("lifeCheck", "MainActivityOnPause");
        super.onPause();
    }

    private void startAnimation(){
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        progressAnimator.setDuration(1250);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.game_BTN_play:

                    play.setVisibility(View.INVISIBLE);
                    CountDownTimer countDownTimer = new CountDownTimer(26 * intervalTime, intervalTime) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            Log.d(TAG, "-> onTick: " + millisUntilFinished);
                            nextRound();
                        }

                        @Override
                        public void onFinish() {
                            Log.d(TAG, "-> onFinish");
                            openActivity(WinnerActivity.class);
                        }
                    };
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            countDownTimer.start();
                        }
                    });
                    thread.start();

                    break;
            }
        }
    };

    private void nextRound() {
        startSound(R.raw.card_flip_sound, 50);
        startAnimation();

        // Get top cards:
        String[] topCards = cardsDeck.getTopCards();
        Log.d(CardsDeck.TAG, topCards[0] + " | " + topCards[1]);

        // Show cards.
        leftCardImg.setImageResource(getResources().getIdentifier(topCards[0], "drawable", getPackageName()));
        rightCardImg.setImageResource(getResources().getIdentifier(topCards[1], "drawable", getPackageName()));

        // Get winner.
        switch (CardsDeck.getWinner(topCards[0], topCards[1])) {
            case 0:
                // Number of draws + 1
                draws++;
                break;
            case 1:
                leftPlayerScore++;
                leftScore.setText("" + leftPlayerScore);
                break;
            case 2:
                rightPlayerScore++;
                rightScore.setText("" + rightPlayerScore);
        }
    }

    private void openActivity(Class activity) {
        Intent myIntent = new Intent(GameActivity.this, activity);
        myIntent.putExtra("LEFT_SCORE",String.valueOf(leftScore.getText()));
        myIntent.putExtra("RIGHT_SCORE",String.valueOf(rightScore.getText()));
        myIntent.putExtra("PLAYER_ONE_NAME",firstPlayerName.getText().toString());
        myIntent.putExtra("PLAYER_TWO_NAME",secondPlayerName.getText().toString());
        startActivity(myIntent);
        finish();
    }


    private void startSound(int soundFile, int soundOffSet){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), soundFile);
                mp.seekTo(soundOffSet);
                mp.start();
            }
        }).start();
    }

}
