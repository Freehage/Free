package com.example.free_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

// Implement OnMapReadyCallback.
public class Recycle3Activity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        setContentView(R.layout.activity_recycle3);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>제로웨이스트샵 찾아보기 </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;


        //double값 입력
        LatLng latLng1 = new LatLng(37.55377859246727, 126.91165214178001);
        MarkerOptions markerOptions1 = new MarkerOptions().position(latLng1).title("알맹상점").snippet("리필, 소분, 제로웨이스트, 플라스틱 프리 공간");
        googleMap.addMarker(markerOptions1);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.getTitle().equals("알맹상점")){
                    String url = "https://blog.naver.com/almangmarket/";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }

            }
        });


        LatLng latLng2 = new LatLng(37.55518657281039, 126.96954432642467);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng2));
        MarkerOptions markerOptions2 = new MarkerOptions().position(latLng2).title("알맹상점 리스테이션");
        googleMap.addMarker(markerOptions2);

        //37.533583306777174, 127.12550848410923 송포어스
        LatLng latLng3 = new LatLng(37.533583306777174, 127.12550848410923);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng3));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        MarkerOptions markerOptions3 = new MarkerOptions().position(latLng3).title("송포어스");
        googleMap.addMarker(markerOptions3);



        //처음화면
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback(){
            @Override
            public void onMapLoaded(){
                LatLng start_position = new LatLng(37.576113135555595, 126.97848352591062);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(start_position));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));
            }
        });




    }


}