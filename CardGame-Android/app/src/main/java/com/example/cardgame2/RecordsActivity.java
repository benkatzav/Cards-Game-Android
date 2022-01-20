package com.example.cardgame2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {
    private ImageView playAgain;
    private TextView playAgainTV;
    private RecordsTable recordsTable;
    SharedPreferences sp;
    ListView records;
    ArrayAdapter<String> contactAdapter;
    private LocationManager locationManager;
    LatLng latLng = null;
    ArrayList<Double> coordinates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("lifeCheck", "RecordsActivityOnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
            setContentView(R.layout.activity_records);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        playAgain = findViewById(R.id.game_BTN_playAgain);
        playAgain.setOnClickListener(onClickListener);
        playAgainTV = findViewById(R.id.game_TV_playAgain);
        playAgainTV.setOnClickListener(onClickListener);

        sp = getSharedPreferences("Records", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        Gson gson = new Gson();

        coordinates = new ArrayList<>();

        recordsTable = new RecordsTable();

        Type type = new TypeToken<ArrayList<Double>>(){}.getType();

        for (int i = 1; i <= RecordsTable.TABLE_SIZE; i++) {
            recordsTable.setRecordsTable(sp.getString("Player" + i, "Name: ---, Score: 0"), (i - 1));
        }

        try{
            recordsTable.setLocations(gson.fromJson(sp.getString("LOCATIONS_LIST", null),type));
        } catch (NullPointerException e){
            for (int i = 1; i <= RecordsTable.TABLE_SIZE*2; i++) {
                coordinates.add(0.0);
            }
            recordsTable.setLocations(coordinates);
        }


        if (Integer.valueOf(getIntent().getExtras().getInt("KEY")) == 1) {
            Boolean bool1 = recordsTable.addToRecordsTable(getIntent().getStringExtra("PlayerOneName"), getIntent().getExtras().getString("PlayerOneScore"),location.getLatitude(),location.getLongitude());
            Boolean bool2 = recordsTable.addToRecordsTable(getIntent().getExtras().getString("PlayerTwoName"), getIntent().getExtras().getString("PlayerTwoScore"),location.getLatitude(),location.getLongitude());

            if(bool1 || bool2) {
                String json = gson.toJson(recordsTable.getLocationsList(), type);
                editor.putString("LOCATIONS_LIST", json);

                for (int i = 1; i <= RecordsTable.TABLE_SIZE; i++) {
                    editor.putString("Player" + i, recordsTable.toString(i - 1));
                }

                editor.apply();
            }
        }

        records = findViewById(R.id.record_LST_recordsList);
        contactAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recordsTable.getList());
        records.setAdapter(contactAdapter);

        Map_Fragment fragment_map = new Map_Fragment(latLng);
        getSupportFragmentManager().beginTransaction().add(R.id.record_FRG_map, fragment_map).commit();

        records.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                latLng = recordsTable.getLatLng(position);
                Map_Fragment fragment_map = new Map_Fragment(latLng);
                getSupportFragmentManager().beginTransaction().add(R.id.record_FRG_map, fragment_map).commit();
            }
        });

    }


    @Override
    protected void onStart() {
        Log.d("lifeCheck", "RecordsActivityOnStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("lifeCheck", "RecordsActivityOnResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d("lifeCheck", "RecordsActivityOnStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("lifeCheck", "RecordsActivityOnDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d("lifeCheck", "RecordsActivityOnPause");
        super.onPause();
    }
    private void openActivity(Class activity) {
        Intent myIntent = new Intent(RecordsActivity.this, activity);
        startActivity(myIntent);
        finish();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case(R.id.game_BTN_playAgain):
                case(R.id.game_TV_playAgain):
                    openActivity(MenuActivity.class);
                    break;
            }
        }
    };


}