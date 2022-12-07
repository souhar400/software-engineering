package de.gruppe.e.klingklang.viewmodel;

import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

import de.gruppe.e.klingklang.BuildConfig;
import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.NamedLocation;

public class FacadeMapView extends AppCompatActivity {
    private static final String MAP_FRAGMENT_TAG = "org.osmdroid.MAP_FRAGMENT_TAG";
    private FacadeMapFragment facadeMapFragment;
    private MyLocationNewOverlay locationOverlay;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<NamedLocation> locations = new ArrayList<>();
        locations = getIntent().getParcelableArrayListExtra(getString(R.string.location_arraylist_parcelid));
        this.setContentView(R.layout.activity_mapview);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        MapView view = findViewById(R.id.mapview);
        view.setTilesScaledToDpi(true);
        view.setMultiTouchControls(true);
        locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), view);
        locationOverlay.enableMyLocation();
        locationOverlay.enableFollowLocation();
        IMapController mapController = view.getController();
        view.setMinZoomLevel(10.0);
        mapController.setZoom(18.0);
        runOnUiThread(() -> mapController.animateTo(locationOverlay.getMyLocation()));
        locations.stream().map(l -> new GeoPoint(l.getLatitude(), l.getLongitude()))
                .forEach(g -> {
                    Marker mark = new Marker(view);
                    mark.setPosition(g);
                    view.getOverlays().add(mark);
                });
        view.getOverlays().add(locationOverlay);
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(view);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset((int) (dm.widthPixels * 0.1), (int) (dm.heightPixels * 0.9));
        view.getOverlays().add(scaleBarOverlay);
        view.setMultiTouchControls(true);
//        FragmentManager fm = this.getSupportFragmentManager();
//        if (fm.findFragmentByTag(MAP_FRAGMENT_TAG) == null) {
//            facadeMapFragment = FacadeMapFragment.newInstance();
//            fm.beginTransaction().add(R.id.mapview, facadeMapFragment, MAP_FRAGMENT_TAG).commit();
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationOverlay.disableMyLocation();
        locationOverlay.disableFollowLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
        locationOverlay.enableMyLocation();
        locationOverlay.enableFollowLocation();
    }
}
