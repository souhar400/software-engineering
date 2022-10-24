package com.teame.klingklang.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.Objects;

public class FacadeProximityBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = FacadeProximityBroadcastReceiver.class.getSimpleName();
    private static final String ERRORMESSAGE_GEOFENCINGEVENT_NULL = "GeofencingEvent could not be fetched from intent: %s";
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
        if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            // TODO: send push-message, localize push message?
        } else {
            // TODO: error-handling
        }

    }
}
