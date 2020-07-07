package com.example.hikerswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText lat;
    EditText longi;
    EditText alt;
    EditText acc;
       LocationManager locationManager;
       LocationListener locationListener;
       public void updateLocationInfo(Location location){

             lat.setText("Latitude: " + Double.toString(location.getLatitude()));
             longi.setText("Longitude:" + Double.toString(location.getLongitude()));
             alt.setText("Altitude: " + Double.toString(location.getAltitude()));
             acc.setText("Accuracy: " + Double.toString(location.getAccuracy()));



       }
    public void startListening() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 0 , 0, locationListener);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);



        if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
            startListening();
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lat = findViewById(R.id.editText1);
        longi = findViewById(R.id.editText2);
        alt = findViewById(R.id.editText3);
        acc = findViewById(R.id.editText4);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                updateLocationInfo(location);

            }
        };
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 0 , 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastKnownLocation != null ){
                updateLocationInfo(lastKnownLocation);

            }
        }

    }
}