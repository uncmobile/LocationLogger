package com.example.locationtestathome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    GoogleApiClient c = null;
    LocationRequest req = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        req = new LocationRequest();
        req.setInterval(3000);
        req.setFastestInterval(1000);
        req.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        c = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        try {
            Location loc = LocationServices.FusedLocationApi.getLastLocation(c);
            Log.v("MYTAG", "Last Location: " + loc.getLatitude() + "," + loc.getLongitude());
            LocationServices.FusedLocationApi.requestLocationUpdates(c, req, this);
        }catch (SecurityException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        c.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        c.disconnect();
    }

    @Override
    public void onLocationChanged(Location loc) {
        Log.v("MYTAG", "Location: " + loc.getLatitude() + "," + loc.getLongitude());
    }
}