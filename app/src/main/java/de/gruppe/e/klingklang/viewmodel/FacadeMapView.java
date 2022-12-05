package de.gruppe.e.klingklang.viewmodel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import de.gruppe.e.klingklang.BuildConfig;
import de.gruppe.e.klingklang.R;

public class FacadeMapView extends AppCompatActivity {
    private static final String MAP_FRAGMENT_TAG = "org.osmdroid.MAP_FRAGMENT_TAG";
    private FacadeMapFragment facadeMapFragment;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        double latitude = getIntent().getExtras().getDouble(getString(R.string.location_latitude));
        double longitude = getIntent().getExtras().getDouble(getString(R.string.location_longitude));
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_mapview);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        //noinspection ConstantConditions
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

//        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        MapView view = findViewById(R.id.mapview);
        view.setTilesScaledToDpi(true);
        view.setMultiTouchControls(true);
        IMapController mapController = view.getController();
        mapController.setZoom(18.0);
        GeoPoint startPoint = new GeoPoint(latitude, longitude);
        mapController.setCenter(startPoint);
        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(view);
        mRotationGestureOverlay.setEnabled(true);
        view.setMultiTouchControls(true);
        view.getOverlays().add(mRotationGestureOverlay);
//        FragmentManager fm = this.getSupportFragmentManager();
//        if (fm.findFragmentByTag(MAP_FRAGMENT_TAG) == null) {
//            facadeMapFragment = FacadeMapFragment.newInstance();
//            fm.beginTransaction().add(R.id.mapview, facadeMapFragment, MAP_FRAGMENT_TAG).commit();
//        }
    }
}
