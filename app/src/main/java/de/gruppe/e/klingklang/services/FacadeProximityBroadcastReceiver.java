package de.gruppe.e.klingklang.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.Objects;
import java.util.Optional;

public class FacadeProximityBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = FacadeProximityBroadcastReceiver.class.getSimpleName();
    private static final String ERRORMESSAGE_GEOFENCINGEVENT_NULL = "GeofencingEvent could not be fetched from intent: %s";
    private static final String LOGMESSAGEG_ENTERED_GEOFENCE = "Entered Geofence: %s";

    private final NotificationService<String> notificationService;

    public FacadeProximityBroadcastReceiver(NotificationService<String> notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(intent);
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent == null) {
            String errorMessage = String.format(ERRORMESSAGE_GEOFENCINGEVENT_NULL, intent.getAction());
            Log.e(TAG, errorMessage);
            return;
        }
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        Optional<Location> location = Optional.ofNullable(geofencingEvent.getTriggeringLocation());
        if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Log.d(TAG, String.format(LOGMESSAGEG_ENTERED_GEOFENCE, geofencingEvent.getTriggeringLocation().toString()));
            location.ifPresent( l -> notificationService.pushNotification("Test") );
        }
    }
}
