package com.example.hackatonv1;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.SphericalUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.android.gms.location.Geofence.NEVER_EXPIRE;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView statue_name;
    private Image statue;
    private Button popup_quit, popup_goto;
    private GoogleMap mMap;
    //private static final String TAG = "GoogleMapsActivity";
    //private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String TAG = "GoogleMapsActivity";

    //Vars
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting device location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                mFusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location currentLocation) {
                                // Got last known location. In some rare situations this can be null.
                                if (currentLocation != null) {
                                    LatLng myLoc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 14f));

                                    LatLng leuven = new LatLng(50.87537708693789, 4.715706763709548);
                                    double distance = SphericalUtil.computeDistanceBetween(leuven, myLoc);
                                    Toast.makeText(GoogleMapsActivity.this, Double.toString(distance), Toast.LENGTH_SHORT).show();

                                    //moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 14f);
                                }else{
                                    Log.d(TAG, "onComplete: current location is NULL");
                                    Toast.makeText(GoogleMapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "GetDeviceLocation: SecurityException " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "Moving the camera");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(this);
                }
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    if (mapFragment != null) {
                        mapFragment.getMapAsync(this);
                    }
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }

        //MAKE GEOFENCE


        double[] latitudes ={50.869818657388684, 50.88484448348449, 50.88884640846997, 50.878230595214795, 50.87537708693789};
        double[] longitudes ={4.716648173905761, 4.69895582413673, 4.696270574295127, 4.691407137829304, 4.715706763709548};
        String[] markerNames ={"Tombstone Vital De Coster", "Klein Begijnhof Leuven", "Keizersberg Abdij", "Kruidtuin", "Hackathon Rodenbach"};
        List<LatLng> markerList=new ArrayList<LatLng>();
        for (int i = 0 ; i < latitudes.length; i++){
            markerList.add(new LatLng(latitudes[i],longitudes[i]));
            mMap.addMarker(new MarkerOptions().position(new LatLng(latitudes[i],longitudes[i])).title(markerNames[i]));
        };

        // Add a marker in Sydney and move the camera
        LatLng leuven = new LatLng(50.8798, 4.7005);
        //mMap.addMarker(new MarkerOptions().position(leuven).title("Marker in Leuven"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(leuven, 14f));
    }

    public void createNewPopUp(int number){
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup_spot_1, null);
    }

}