package com.example.hackatonv1;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.security.keystore.StrongBoxUnavailableException;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.SphericalUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.hackatonv1.Globals.getLang;
import static com.example.hackatonv1.Globals.setLang;
import static com.example.hackatonv1.Globals.setName;
import static com.example.hackatonv1.Globals.setId;

import static com.google.android.gms.location.Geofence.NEVER_EXPIRE;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView statue_name;
    private Image statue;
    private Button popup_quit, popup_goto;
    int[] images;
    String[] names;
    Context context;
    Resources resources;


    private GoogleMap mMap;
    //private static final String TAG = "GoogleMapsActivity";
    //private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private GeofencingClient geofencingClient;

    private static final String TAG = "GoogleMapsActivity";
    private List<Geofence> geofenceList = new ArrayList<>();

    //Vars
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        getLocationPermission();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used

        Button secretBtn = (Button) findViewById(R.id.qrButton);
        secretBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoogleMapsActivity.this, qrCodePage.class));
            }
        });

        Button shopsBtn = (Button) findViewById(R.id.shopsButton);
        shopsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] latitudes = {50.878046271875114, 50.88780911837633, 50.8771438663048};
                double[] longitudes = {4.699030469592737, 4.700266911713537, 4.700555142818541};
                String[] markerNames = {"Café Belge", "Café Entrepot", "Noir Coffeebar"};
                List<LatLng> markerList = new ArrayList<LatLng>();
                for (int i = 0; i < latitudes.length; i++) {
                    markerList.add(new LatLng(latitudes[i], longitudes[i]));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitudes[i], longitudes[i])).title(markerNames[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.cafemarker)));
                }

                //50.879538289908695, 4.705907127264086
                double[] latitudes2 = {50.879538289908695, 50.880554010521955, 50.88046510762541};
                double[] longitudes2 = {4.705907127264086, 4.695961813771077, 4.70889560384445};
                String[] markerNames2 = {"fnac", "Objets Trouvés", "Kruidvat"};
                List<LatLng> markerList2 = new ArrayList<LatLng>();
                for (int i = 0; i < latitudes2.length; i++) {
                    markerList2.add(new LatLng(latitudes2[i], longitudes2[i]));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitudes2[i], longitudes2[i])).title(markerNames2[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.shopicon)));
                }
            }
        });
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
                                    //Toast.makeText(GoogleMapsActivity.this, Double.toString(distance), Toast.LENGTH_SHORT).show();

                                    //moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 14f);
                                } else {
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

        double[] latitudes = {50.869818657388684, 50.88484448348449, 50.88884640846997, 50.878230595214795, 50.87537708693789};
        double[] longitudes = {4.716648173905761, 4.69895582413673, 4.696270574295127, 4.691407137829304, 4.715706763709548};
        String[] markerNames = {"Tombstone Vital De Coster", "Klein Begijnhof Leuven", "Keizersberg Abdij", "Kruidtuin", "Hackathon Rodenbach"};

        List<LatLng> markerList = new ArrayList<LatLng>();
        for (int i = 0; i < latitudes.length; i++) {
            markerList.add(new LatLng(latitudes[i], longitudes[i]));
            switch (markerNames[i]) {
                case "Tombstone Vital De Coster":
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitudes[i], longitudes[i])).title(markerNames[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.andreas_vesalius_mini)));
                    break;

                case "Klein Begijnhof Leuven":
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitudes[i], longitudes[i])).title(markerNames[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.erasmus_mini)));
                    break;

                case "Keizersberg Abdij":
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitudes[i], longitudes[i])).title(markerNames[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.mercator_mini)));
                    break;

                case "Kruidtuin":
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitudes[i], longitudes[i])).title(markerNames[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.fiere_margriet_mini)));
                    break;

                case "Hackathon Rodenbach":
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitudes[i], longitudes[i])).title(markerNames[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.justus_lipsius_mini)));
                    break;

                default:
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitudes[i], longitudes[i])).title(markerNames[i]));
                    break;
            }
            //mMap.addMarker(new MarkerOptions().position(new LatLng(latitudes[i], longitudes[i])).title(markerNames[i]));
        }
        ;

        // Add a marker in Sydney and move the camera
        LatLng leuven = new LatLng(50.8798, 4.7005);
        //mMap.addMarker(new MarkerOptions().position(leuven).title("Marker in Leuven"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(leuven, 14f));


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker m) {
                switch (m.getTitle()) {
                    case "Tombstone Vital De Coster":
                        setName("Vesalius");
                        setId("andreas_vesalius");
                        break;

                    case "Klein Begijnhof Leuven":
                        setName("Erasmus");
                        setId("erasmus");
                        break;

                    case "Keizersberg Abdij":
                        setName("Mercator");
                        setId("mercator");
                        break;

                    case "Kruidtuin":
                        setName("Fiere Margriet");
                        setId("fiere_margriet");
                        break;

                    case "Hackathon Rodenbach":
                        setName("Rodenbach");
                        setId("justus_lipsius");
                        break;

                    default:
                        return false;
                }
                startActivity(new Intent(GoogleMapsActivity.this, PopupActivity.class));
                return true;
            }
        });
    }
}
