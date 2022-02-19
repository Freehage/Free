package com.example.free_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.free_app.database.DatabaseHelper2;
import com.example.free_app.model.ZeroShop;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

// Implement OnMapReadyCallback.
public class Recycle3Activity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap googleMap;

    //public List<ZeroShop> marketLists;
    public List<ZeroShop> marketList;

    private void initLoadDB2(){

        DatabaseHelper2 databaseHelper = new DatabaseHelper2(getApplicationContext());
        databaseHelper.openDB();

        marketList = databaseHelper.getTableData();
        Log.e("TEST",String.valueOf(marketList.size()));
        Log.e("1",marketList.get(0).storeName);
        databaseHelper.close();
    }



    //홈버튼 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu)    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home_button:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle3);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_space);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>제로웨이스트샵 찾아보기 </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initLoadDB2();
        Log.e("TEST",String.valueOf(marketList.size()));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }


    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        for (int i = 0; i < marketList.size(); i++) {

            String storeName = marketList.get(i).storeName;     // 이름
            double lat = marketList.get(i).latitude;            // 위도
            double lon = marketList.get(i).longitude;           // 경도
            String url = marketList.get(i).url;                 // 웹사이트 주소
            String detail = marketList.get(i).detail;           // 설명
            String address = marketList.get(i).address;         // 주소


            LatLng latLng = new LatLng(lat, lon);


            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng).title(storeName).snippet(detail);
            googleMap.addMarker(markerOptions);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        }



        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                for (int i = 0; i < marketList.size(); i++) {
                    String storeName = marketList.get(i).storeName;     // 이름
                    String url = marketList.get(i).url;                 // 웹사이트 주소
                    if (marker.getTitle().equals(storeName)) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                }
            }
        });

            //처음화면
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLng start_position = new LatLng(37.576113135555595, 126.97848352591062);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(start_position));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));
            }
        });
    }

}