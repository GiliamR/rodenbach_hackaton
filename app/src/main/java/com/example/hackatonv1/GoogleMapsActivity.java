package com.example.hackatonv1;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //private static final String TAG = "GoogleMapsActivity";
    //private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    //Vars
    private Boolean mLocationPermissionsGranted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        getLocationPermission();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used
    }

    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
            }else{
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        mLocationPermissionsGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
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

    /*public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(GoogleMapsActivity.this);
        if(available == ConnectionResult.SUCCESS){
            //everything works well
            Log.d(TAG, "isServicesOK: Google Play services are working");
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //error but can be resolved
            Log.d(TAG, "isServicesOK: an error but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(GoogleMapsActivity.this, available, ERROR_DIALOG_REQUEST);
        }else{
            Toast.makeText(this, "can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }*/

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

        /*try {
            FileInputStream fIn = openFileInput("markerFile");
            int c;
            String temp = "";
            while ((c = fIn.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        double[] latitudes ={50.869818657388684, 50.88884640846997, 50.878230595214795};
        double[] longitudes ={4.716648173905761, 4.696270574295127, 4.691407137829304};
        String[] markerNames ={"Tombstone Vital De Coster", "Keizersberg Abdij", "Kruidtuin"};
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


}