package com.example.cardgame2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity {
    private ImageView startGame;
    private ImageView clipBoard;
    private EditText playerOneName;
    private EditText playerTwoName;
    String player1,player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("lifeCheck", "MenuActivityOnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {

            setContentView(R.layout.activity_main);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MenuActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }

        startGame = findViewById(R.id.menu_BTN_startGame);
        clipBoard = findViewById(R.id.menu_BTN_clipBoard);
        playerOneName = findViewById(R.id.menu_EDT_firstName);
        playerTwoName = findViewById(R.id.menu_EDT_secondName);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MenuActivity.this , GameActivity.class);
                player1=playerOneName.getText().toString();
                player2 = playerTwoName.getText().toString();
                mIntent.putExtra("Value1",player1);
                mIntent.putExtra("Value2",player2);
                startActivity(mIntent);
                finish();
            }
        });

        clipBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MenuActivity.this , RecordsActivity.class);
                mIntent.putExtra("KEY",2);
                startActivity(mIntent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        Log.d("lifeCheck", "MenuActivityOnStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("lifeCheck", "MenuActivityOnResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d("lifeCheck", "MenuActivityOnStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("lifeCheck", "MenuActivityOnDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d("lifeCheck", "MenuActivityOnPause");
        super.onPause();
    }
}