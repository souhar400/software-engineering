package de.gruppe.e.klingklang.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.Objects;
import java.util.Optional;

import de.gruppe.e.klingklang.viewmodel.MainActivity;
import de.gruppe.e.klingklang.R;

/**
 * This {@link BroadcastReceiver} handles Intents regarding {@link Geofence Geofences}.
 * Whenever a triggering {@link Geofence} is entered, a {@link android.app.Notification} is triggered.
 * This {@link android.app.Notification} will inform the user about entering one of the defined
 * {@link Geofence Geofences}.
 *
 */
public class FacadeProximityBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = FacadeProximityBroadcastReceiver.class.getSimpleName();
    private static final String ERRORMESSAGE_GEOFENCINGEVENT_NULL = "GeofencingEvent could not be fetched from intent: %s";
    private static final String LOGMESSAGE_ENTERED_GEOFENCE = "Entered Geofence: %s";
    private static final String NOTIFY_CHANNEL = "KLINGKLANG_NOTIFICATION_CHANNEL";
    private static final String NOTIFICATION_LONG_MESSAGE = "You are nearby %s. Take a look around!";
    private static final String NOTIFICATION_SHORT_MESSAGE = "Now nearby: %s";

    public FacadeProximityBroadcastReceiver() {
        Log.d(TAG, "Instantiated " + TAG);
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
            Log.d(TAG, String.format(LOGMESSAGE_ENTERED_GEOFENCE, geofencingEvent.getTriggeringLocation().toString()));
            NamedLocationNotificationService notificationService = NamedLocationNotificationService.getInstance();
            location.ifPresent(l -> notificationService.sendNotification(context, l.getLatitude(), l.getLongitude()));
        }
    }
}
