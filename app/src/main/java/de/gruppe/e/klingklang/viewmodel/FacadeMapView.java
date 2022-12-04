package de.gruppe.e.klingklang.viewmodel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

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
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_mapview);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        //noinspection ConstantConditions
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

//        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        MapView view = findViewById(R.id.mapview);
        view.setExpectedCenter(new GeoPoint(51.95109, 7.98756));
        view.setMaxZoomLevel(18.0);
        view.setMinZoomLevel(18.0);
        FragmentManager fm = this.getSupportFragmentManager();
        if (fm.findFragmentByTag(MAP_FRAGMENT_TAG) == null) {
            facadeMapFragment = FacadeMapFragment.newInstance();
            fm.beginTransaction().add(R.id.mapview, facadeMapFragment, MAP_FRAGMENT_TAG).commit();
        }
    }
}
