package com.example.apple.myweatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.apple.weatherapplication.R;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String address = "";
    String currentDate = "";
    Geocoder geocoder;
    TextView addressTextView, dateTextView;
    EditText cityEditText;

    static TextView nameTextView, temperatureTextView;
    LocationManager locationManager;
    LocationListener locationListener;

    private Double latitude;
    private Double longitude;

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressTextView = (TextView) findViewById(R.id.addressTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        cityEditText = (EditText) findViewById(R.id.cityEditText);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);

        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        dateTextView.setText(currentDate);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
       // Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       // final double latitude = location.getLongitude();
        //final double longitude = location.getLatitude();

        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
//
//        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//
//        try {
//
//            List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
//
//            if (listAddresses != null && listAddresses.size() > 0) {
//
//                Log.i("Place info", listAddresses.get(0).toString());
//
//                if (listAddresses.get(0).getLocality() != null) {
//
//                    address += listAddresses.get(0).getLocality() + ", ";
//
//                }
//
//                if (listAddresses.get(0).getCountryName() != null) {
//
//                    address += listAddresses.get(0).getCountryName();
//                    addressTextView.setText(address);
//                    //address = "";
//
//                }
//
//            }
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        }
//
//        DownloadTask task = new DownloadTask();
//        try {
//
//            task.execute("http://openweathermap.org/data/2.5/weather?lat=" + String.valueOf(latitude) + "&lon=" + String.valueOf(longitude) + "&appid=b6907d289e10d714a6e88b30761fae22").get();
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //ask for permision
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
    }
}



