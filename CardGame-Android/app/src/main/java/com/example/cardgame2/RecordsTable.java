package com.example.cardgame2;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

public class RecordsTable extends AppCompatActivity {
    public static final String TAG = "RecordsTable";
    public static final int TABLE_SIZE = 10;
    private String[] recordsTable;
    private LatLng[] locations;


    public RecordsTable() {
        this.recordsTable = new String[TABLE_SIZE];
        locations = new LatLng[TABLE_SIZE];
        for(int i = 0; i < TABLE_SIZE; i++) {
            recordsTable[i] = "Name: ---, Score: 0";
            locations[i] = null;
        }
    }

    public boolean addToRecordsTable(String name, String score,Double lat, Double lng){
        for(int i = 0; i < TABLE_SIZE; i++){
            if(checkScore(recordsTable[i]) < Integer.valueOf(score)){
                recordsTable[TABLE_SIZE -1] = ("Name: "+name+", Score: "+score);
                locations[TABLE_SIZE -1] = null;
                locations[TABLE_SIZE -1] = new LatLng(lat,lng);
                sortTable();
                return true;
            }
        }
        return false;
    }

    public void sortTable(){
        for(int i = 0; i < TABLE_SIZE -1; i++){
            for(int j = 0; j < TABLE_SIZE -i-1; j++){
                if(checkScore(recordsTable[j]) < checkScore(recordsTable[j+1])){
                    String temp = recordsTable[j];
                    LatLng tempLocation = locations[j];
                    recordsTable[j] = recordsTable[j+1];
                    locations[j] = locations[j+1];
                    recordsTable[j+1] = temp;
                    locations[j+1] = tempLocation;
                }
            }
        }
    }

    private int checkScore(String str){
        String temp = str.substring(1);
        for (int i = 0; i < str.length();) {
            if(!(temp.charAt(i) > 47 && temp.charAt(i) < 58))
                temp = temp.substring(i+1);
            else
                break;
        }
        return Integer.valueOf(temp);
    }


    public void setRecordsTable(String str,int i){
        recordsTable[i] = str;
    }

    public String toString(int i){
        return recordsTable[i];
    }

    public String[] getList(){
        return recordsTable;
    }

    public LatLng getLatLng(int i){
        return locations[i];
    }

    public ArrayList<Double> getLocationsList(){
        ArrayList<Double> coordinates = new ArrayList<>();
        for(int i = 0; i < TABLE_SIZE; i++){
            if(locations[i] != null) {
                coordinates.add(locations[i].latitude);
                coordinates.add(locations[i].longitude);
            }
        }

        return coordinates;
    }

    public void setLocations(ArrayList<Double> coordinates){
        int num = 0;
        for(int i = 0; i < TABLE_SIZE; i++){
            locations[i] = new LatLng(coordinates.get(num),coordinates.get(num+1));
            num = num +2;
        }

    }
}
