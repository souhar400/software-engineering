package de.gruppe.e.klingklang.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
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

import de.gruppe.e.klingklang.R;

public class FacadeProximityBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = FacadeProximityBroadcastReceiver.class.getSimpleName();
    private static final String ERRORMESSAGE_GEOFENCINGEVENT_NULL = "GeofencingEvent could not be fetched from intent: %s";
    private static final String LOGMESSAGE_ENTERED_GEOFENCE = "Entered Geofence: %s";
    private static final String NOTIFY_CHANNEL = "KLINGKLANG_NOTIFICATION_CHANNEL";
    private static final String NOTIFY_TITLE = "KlingKlang";

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
            location.ifPresent( l -> sendNotification(context,"Test") );
        }
    }

    private void sendNotification(Context context, String msg) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFY_CHANNEL)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setContentTitle(NOTIFY_TITLE)
                .setContentText(msg)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(msg));
        createNotificationChannel(context);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        assert notificationManager != null;
        notificationManager.notify(0, builder.build());
    }

    private void createNotificationChannel(Context context) {
        CharSequence name = context.getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(NOTIFY_CHANNEL, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }

}
