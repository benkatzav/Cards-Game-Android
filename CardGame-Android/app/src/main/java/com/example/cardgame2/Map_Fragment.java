package com.example.cardgame2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map_Fragment extends Fragment {
    LatLng latLng;

    public Map_Fragment(LatLng latLng) {
      this.latLng = latLng;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
       View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment SMF = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.mapFragment);
        SMF.getMapAsync(new OnMapReadyCallback() {
            @Override
           public void onMapReady(GoogleMap googleMap) {
                if(latLng != null)
               placeMarker(latLng,googleMap);
            }
        });
        return view;
    }


    public void placeMarker(LatLng latLng, GoogleMap googleMap){
        googleMap.addMarker(new MarkerOptions().position(latLng).title(latLng.latitude+ ":" + latLng.longitude));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 6));

    }

}