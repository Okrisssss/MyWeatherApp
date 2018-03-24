package com.example.apple.myweatherapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.apple.weatherapplication.BuildConfig;
import com.example.apple.weatherapplication.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = MainActivity.class.getSimpleName();

  private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

  /**
   * Represents a geographical location.
   */

  private String mLatitudeLabel;
  private String mLongitudeLabel;
  private TextView mLatitudeText;
  private TextView mLongitudeText;
  private TextView mAddressText;
  private LocationManager mLocationManager;
  private LocationListener mLocationListener;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mLatitudeLabel = getResources().getString(R.string.latitude_label);
    mLongitudeLabel = getResources().getString(R.string.longitude_label);
    mLatitudeText = (TextView) findViewById((R.id.latitude_text));
    mLongitudeText = (TextView) findViewById((R.id.longitude_text));

    mLocationListener = new LocationListener() {
      @Override
      public void onLocationChanged(final Location location) {
        if (location != null) {
          updateLocationName(location);
          mLatitudeText.setText(mLatitudeLabel + " is " + String.valueOf(location.getLatitude()));
          mLongitudeText.setText(mLongitudeLabel + " is " + String.valueOf(location.getLongitude()));
        } else {
          mLatitudeText.setText("Latitude is null");
          mLongitudeText.setText("Longitude is null");
        }
      }

      @Override
      public void onStatusChanged(String provider, int status, Bundle extras) {
        // No need to implement for now
      }

      @Override
      public void onProviderEnabled(String provider) {
        // No need to implement for now
      }

      @Override
      public void onProviderDisabled(String provider) {
        // No need to implement for now
      }
    };

    mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
  }

  @Override
  public void onStart() {
    super.onStart();

    if (!checkPermissions()) {
      requestPermissions();
    } else {
      getLastLocation();
    }
  }

  /**
   * Provides a simple way of getting a device's location and is well suited for
   * applications that do not require a fine-grained location and that do not need location
   * updates. Gets the best and most recent location currently available, which may be null
   * in rare cases when a location is not available.
   * <p>
   * Note: this method should be called after location permission has been granted.
   */

  // THIS IS VERY IMPORTANT ANNOTATION!!!!! It allows to write code independent of requesting permissions BRO.
  @SuppressWarnings("MissingPermission")
  private void getLastLocation() {
    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,
            10, mLocationListener);

  }

  /**
   * Shows a {@link Snackbar}.
   *
   * @param mainTextStringId The id for the string resource for the Snackbar text.
   * @param actionStringId   The text of the action item.
   * @param listener         The listener associated with the Snackbar action.
   */

  //You may be asking what is SnackBar. It's something like Toast but it's clikcable and ahs listener for that click.
  //It appears in the bottom of the app and notify user that he did not grant permissions
  //Try to dismiss permissions when first launch the app and you will what happen
  //Do not worry about SnackBar, bettern UNDERSTAND other code.
  private void showSnackbar(final int mainTextStringId, final int actionStringId,
                            View.OnClickListener listener) {
    Snackbar.make(findViewById(android.R.id.content),
            getString(mainTextStringId),
            Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(actionStringId), listener).show();
  }

  /**
   * Return the current state of the permissions needed.
   */
  private boolean checkPermissions() {
    int permissionStateCoarse = ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION);
    int permissionStateFine = ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION);
    return permissionStateCoarse == PackageManager.PERMISSION_GRANTED && permissionStateFine == PackageManager.PERMISSION_GRANTED;
  }

  private void startLocationPermissionRequest() {
    ActivityCompat.requestPermissions(MainActivity.this,
            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
            REQUEST_PERMISSIONS_REQUEST_CODE);
  }

  private void requestPermissions() {
    boolean shouldProvideRationaleCoarse =
            ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);

    boolean shouldProvideRationaleFine =
            ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
    // Provide an additional rationale to the user. This would happen if the user denied the
    // request previously, but didn't check the "Don't ask again" checkbox.
    if (shouldProvideRationaleCoarse || shouldProvideRationaleFine) {
      Log.i(TAG, "Displaying permission rationale to provide additional context.");

      showSnackbar(R.string.permission_rationale, android.R.string.ok,
              new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  // Request permission
                  startLocationPermissionRequest();
                }
              });

    } else {
      Log.i(TAG, "Requesting permission");
      // Request permission. It's possible this can be auto answered if device policy
      // sets the permission in a given state or the user denied the permission
      // previously and checked "Never ask again".
      startLocationPermissionRequest();
    }
  }

  /**
   * Callback received when a permissions request has been completed.
   */
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    Log.i(TAG, "onRequestPermissionResult");
    if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
      if (grantResults.length <= 0) {
        // If user interaction was interrupted, the permission request is cancelled and you
        // receive empty arrays.
        Log.i(TAG, "User interaction was cancelled.");
      } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        // Permission granted.
        getLastLocation();
      } else {
        // Permission denied.

        // Notify the user via a SnackBar that they have rejected a core permission for the
        // app, which makes the Activity useless. In a real app, core permissions would
        // typically be best requested during a welcome-screen flow.

        // Additionally, it is important to remember that a permission might have been
        // rejected without asking the user for permission (device policy or "Never ask
        // again" prompts). Therefore, a user interface affordance is typically implemented
        // when permissions are denied. Otherwise, your app could appear unresponsive to
        // touches or interactions which have required permissions.
        showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                    // Build intent that displays the App settings screen.
                    Intent intent = new Intent();
                    intent.setAction(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package",
                            BuildConfig.APPLICATION_ID, null);
                    intent.setData(uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                  }
                });
      }
    }
  }
  public void updateLocationName(Location location) {

    Log.i("LocationInfo", location.toString());

    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

    try {

      String address = "Could not find address";

      List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

      if (listAddresses != null && listAddresses.size() > 0) {

        Log.i("PlaceInfo", listAddresses.get(0).toString());

        address = "Address: ";

        if (listAddresses.get(0).getLocality() != null) {

          address += listAddresses.get(0).getLocality() + ", ";

        }

        if (listAddresses.get(0).getCountryName() != null) {

          address += listAddresses.get(0).getCountryName() ;

        }

      }
      mAddressText = (TextView)findViewById(R.id.addres_text);
      mAddressText.setText(address);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}


