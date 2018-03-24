//package com.example.apple.myweatherapp;
//
//
//import android.content.Context;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.util.Log;
//import android.widget.TextView;
//
//import com.example.apple.weatherapplication.R;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//
//public class GettingCurrentLocationName {
//
//    MainActivity mainActivity;
//    private TextView mAddressText;
//
//
//    public void updateLocationName(Location location) {
//
//        Log.i("LocationInfo", location.toString());
//
//        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//
//        try {
//
//            String address = "Could not find address";
//
//            List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//
//            if (listAddresses != null && listAddresses.size() > 0) {
//
//                Log.i("PlaceInfo", listAddresses.get(0).toString());
//
//                address = "Address: \n";
//
//                if (listAddresses.get(0).getSubThoroughfare() != null) {
//
//                    address += listAddresses.get(0).getSubThoroughfare() + " ";
//
//                }
//
//                if (listAddresses.get(0).getThoroughfare() != null) {
//
//                    address += listAddresses.get(0).getThoroughfare() + "\n";
//
//                }
//
//                if (listAddresses.get(0).getLocality() != null) {
//
//                    address += listAddresses.get(0).getLocality() + "\n";
//
//                }
//
//                if (listAddresses.get(0).getPostalCode() != null) {
//
//                    address += listAddresses.get(0).getPostalCode() + "\n";
//
//                }
//
//                if (listAddresses.get(0).getCountryName() != null) {
//
//                    address += listAddresses.get(0).getCountryName() + "\n";
//
//                }
//
//            }
//            mAddressText = (TextView) mainActivity.findViewById(R.id.addres_text);
//            mAddressText.setText(address);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
