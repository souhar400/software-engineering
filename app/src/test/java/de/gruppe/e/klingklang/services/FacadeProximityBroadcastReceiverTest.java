package de.gruppe.e.klingklang.services;

import static org.mockito.Mockito.when;

import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FacadeProximityBroadcastReceiverTest {

    private FacadeProximityBroadcastReceiver classUnderTest;
    private Intent intentMock;
    @Mock
    private GeofencingEvent geofencingEventMock;
    @Mock
    private Location locationMock;
    @Before
    public void init() {
        classUnderTest = new FacadeProximityBroadcastReceiver();
    }
    @Test
    public void testOnReceive_enterIrrelevantLocation() {
        when(GeofencingEvent.fromIntent(intentMock)).thenReturn(geofencingEventMock);
        when(geofencingEventMock.getGeofenceTransition()).thenReturn(Geofence.GEOFENCE_TRANSITION_ENTER);
        when(geofencingEventMock.getTriggeringLocation()).thenReturn(locationMock);
        classUnderTest.onReceive(null, null);
    }

    @Test
    public void testOnReceive_enterRelevantLocation() {

    }

    @Test
    public void testOnReceive_exitLocation() {

    }

    @Test
    public void testOnReceive_lingerInLocation() {

    }

    @Test
    public void testOnReceive_irrelevantIntent() {

    }

    @Test
    public void testOnReceive_relevantIntentNoTrigeringZone() {

    }
}
