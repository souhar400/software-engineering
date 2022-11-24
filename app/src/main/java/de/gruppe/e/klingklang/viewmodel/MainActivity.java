package de.gruppe.e.klingklang.viewmodel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.model.OldViewFacadeData;
import de.gruppe.e.klingklang.services.FacadeProximityBroadcastReceiver;
import de.gruppe.e.klingklang.services.SynthService;
import de.gruppe.e.klingklang.view.ControlButtonsOverlayView;
import de.gruppe.e.klingklang.view.MainMenu;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private static final int LOCATION_REQUEST = 0;
    private static final String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private static String[] backgroundPermissions;
    private PendingIntent geofencePendingIntent;
    private GeofencingClient geofencingClient;
    private final List<Geofence> geofenceList = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationClient;
    private SynthService SynthService;
    private FacadeViewModel facadeViewModel;
    private MainMenu mainMenu;
    private ControlButtonsOverlayView controlButtonsOverlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideNavigationAndSwipeUpBar();
        mainMenu = new MainMenu(getSupportFragmentManager());
        SynthService = new SynthService(this);
        /*
        TODO: this does not work properly
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        */
        setContentView(R.layout.activity_main);
        HashMap<Button, ButtonData> oldViewButtons = initialiseOldViewButtons();
        controlButtonsOverlayView = new ControlButtonsOverlayView(findViewById(R.id.edit_button),
                findViewById(R.id.setting_button),
                mainMenu);
        facadeViewModel = new FacadeViewModel(controlButtonsOverlayView,
                new OldViewFacadeData(),
                SynthService,
                getSupportFragmentManager(),
                oldViewButtons);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geofencingClient = LocationServices.getGeofencingClient(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            backgroundPermissions = new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION};
        }
        buildGeofenceList("R. de Mouzinho da Silveira 42", 41.141, -8.614, 200);
        if (lacksPermissions()) {
            requestPermissions();
        }
        startLocationUpdates();
        addGeofences();
        Log.d(LOG_TAG, "App successfully created!");

    }

    private HashMap<Button,ButtonData> initialiseOldViewButtons() {
        HashMap<Button,ButtonData> map = new HashMap<>();
        map.put((Button) this.findViewById(R.id.top_left1), new ButtonData("klingklang.sf2",5,62,127,5, false));
        map.put((Button) this.findViewById(R.id.top_left2), new ButtonData("klingklang.sf2",0,10,127,0, false));
        map.put((Button) this.findViewById(R.id.bottom_left1), new ButtonData("klingklang.sf2",6,62,127,6, false));
        map.put((Button) this.findViewById(R.id.bottom_left2), new ButtonData("klingklang.sf2",1,62,127,1, false));
        map.put((Button) this.findViewById(R.id.top_middle1), new ButtonData("klingklang.sf2",8,62,127,8, false));
        map.put((Button) this.findViewById(R.id.top_middle2), new ButtonData("klingklang.sf2",8,80,127,8, false));
        map.put((Button) this.findViewById(R.id.bottom_middle1), new ButtonData("klingklang.sf2",10,62,127,10, false));
        map.put((Button) this.findViewById(R.id.bottom_middle2), new ButtonData("klingklang.sf2",2,10,127,2, false));
        map.put((Button) this.findViewById(R.id.top_right1), new ButtonData("klingklang.sf2",4,62,127,4, true));
        map.put((Button) this.findViewById(R.id.top_right2), new ButtonData("klingklang.sf2",3,62,127,3, true));
        map.put((Button) this.findViewById(R.id.bottom_right1), new ButtonData("klingklang.sf2",7,62,127,7, true));
        map.put((Button) this.findViewById(R.id.bottom_right2), new ButtonData("klingklang.sf2",11,90,127,11, true));
        return map;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigationAndSwipeUpBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SynthService.cleanupFluidSynth();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length == 0) {
            return;
        }
        if (requestCode == LOCATION_REQUEST) {
            boolean hasCoarseLocationPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean hasFineLocationPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if (hasFineLocationPermission || hasCoarseLocationPermission) {
                ActivityCompat.requestPermissions(this, backgroundPermissions, LOCATION_REQUEST + 1);
            }
        }
        if (requestCode == LOCATION_REQUEST + 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
                addGeofences();
            }
        }
    }

    /**
     * This is needed to continuously receive location updates, enabling a
     * {@link android.content.BroadcastReceiver} to pick up Intents regarding location-changes.
     */
    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        if (lacksPermissions()) {
            requestPermissions();
        }
        fusedLocationClient.requestLocationUpdates(createLocationRequest(), new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {

            }
        }, Looper.getMainLooper());
    }

    /**
     * @return a {@link LocationRequest} with high accuracy
     */
    private LocationRequest createLocationRequest() {
        LocationRequest.Builder builder = new LocationRequest.Builder(10000).setMinUpdateIntervalMillis(5000).setPriority(Priority.PRIORITY_HIGH_ACCURACY);
        return builder.build();
    }

    public void hideNavigationAndSwipeUpBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    /**
     * Checks whether all permissions required by this app are granted.
     *
     * @return <b>true</b> when all permissions are granted,<b>false</b> otherwise.
     */
    private boolean lacksPermissions() {
        return !Arrays.stream(permissions).allMatch(p -> ActivityCompat.checkSelfPermission(this, p) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Requests permissions from the user.
     */
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST);
    }


    /**
     * Builds a list of one Geofence-Entry to be checked for {@link Geofence#GEOFENCE_TRANSITION_ENTER}
     * and {@link Geofence#GEOFENCE_TRANSITION_EXIT} transition-types.
     * Will be overhauled later to only add elements to a list of geofences.
     *
     * @param id        Geofence-ID for Request
     * @param latitude  Latitude of Location in degrees
     * @param longitude Longitude of Location in degrees
     * @param rad       Radius of circular region defining the geofence around the given location
     */
    private void buildGeofenceList(String id, @FloatRange(from = -90.0, to = 90.0) double latitude, @FloatRange(from = -180.0, to = 180.0) double longitude, @FloatRange(from = 0.0, fromInclusive = false) float rad) {
        geofenceList.add(new Geofence.Builder().setRequestId(id).setCircularRegion(latitude, longitude, rad).setExpirationDuration(Geofence.NEVER_EXPIRE).setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER).build());
    }

    /**
     * Construct the {@link PendingIntent} broadcasting for the {@link FacadeProximityBroadcastReceiver}
     *
     * @return The constructed {@link PendingIntent}
     */
    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, FacadeProximityBroadcastReceiver.class);
        intent.putExtra(getString(R.string.location_region_name), "Porto, Portugal");
        intent.putExtra(getString(R.string.location_region_address), "R. de Mouzinho da Silveira");
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags = flags | PendingIntent.FLAG_MUTABLE;
        }
        geofencePendingIntent = PendingIntent.getBroadcast(this, 1, intent, flags);
        return geofencePendingIntent;
    }

    /**
     * Construct the {@link GeofencingRequest} containing the {@link Geofence Geofences} as
     * constructed by {@link MainActivity#buildGeofenceList}.
     *
     * @return The constructed {@link GeofencingRequest}
     */
    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }


    /**
     * Add the {@link GeofencingRequest GeofencingRequests} and {@link PendingIntent} constructed by
     * {@link MainActivity#getGeofencingRequest()} and {@link MainActivity#getGeofencePendingIntent()}
     * to this activity's {@link GeofencingClient}.
     */
    @SuppressLint("MissingPermission")
    private void addGeofences() {
        if (lacksPermissions()) {
            requestPermissions();
        }
        geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent()).addOnSuccessListener(this, e -> Log.d(LOG_TAG, "Successfully added geofences!")).addOnFailureListener(this, e -> Log.e(LOG_TAG, "Could not add geofences!", e));
    }

}
