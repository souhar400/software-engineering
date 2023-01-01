package de.gruppe.e.klingklang.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Optional;
import java.util.function.Function;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.NamedLocation;
import de.gruppe.e.klingklang.viewmodel.FacadeViewModel;
import de.gruppe.e.klingklang.viewmodel.MainActivity;

public class NamedLocationNotificationService {

    private static NamedLocationNotificationService INSTANCE;
    private static final String NOTIFY_CHANNEL = "KLINGKLANG_NOTIFICATION_CHANNEL";
    private static final String NOTIFICATION_LONG_MESSAGE = "You are nearby %s. Take a look around!";
    private static final String NOTIFICATION_SHORT_MESSAGE = "Now nearby: %s";

    private AppCompatActivity activity;
    private NamedLocationNotificationService(AppCompatActivity activity) {
        this.activity = activity;
    }

    public static NamedLocationNotificationService getInstance() {
        if(INSTANCE == null) {
            throw new IllegalStateException("No Activity has instantiated this Service yet!");
        }
        return INSTANCE;
    }

    public static NamedLocationNotificationService getInstance(AppCompatActivity activity) {
        if(INSTANCE == null) {
            INSTANCE = new NamedLocationNotificationService(activity);
        }
        return INSTANCE;
    }

    public void sendNotification(Context context, double latitude, double longitude) {
        FacadeViewModel facadeViewModel = new ViewModelProvider(activity).get(FacadeViewModel.class);
        Optional<NamedLocation> firstHit = facadeViewModel.getAllLocations().stream()
                .filter(l -> isInRadius(latitude, longitude, l))
                .findFirst();
        sendNotification(context, firstHit.orElseThrow(() ->
                new RuntimeException("Provided location does not correspond to any NamedLocation")));
    }

    private boolean isInRadius(double latitude, double longitude, NamedLocation l) {
        float[] results = new float[1];
        Location.distanceBetween(latitude,
                longitude,
                l.getLatitude(),
                l.getLongitude(),
                results);
        return l.getRadius() - results[0] >= 0;
    }

    private void sendNotification(Context context, NamedLocation location) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFY_CHANNEL)
                .setSmallIcon(R.drawable.ic_product_foreground)
                .setContentTitle(location.getShortName())
                .setContentText(String.format(NOTIFICATION_SHORT_MESSAGE, location.getAddress()))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(String.format(NOTIFICATION_LONG_MESSAGE, location.getAddress())));
        createNotificationChannel(context);

        Intent notifyIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            notifyIntent = new Intent(context, MainActivity.class);
            // Set the Activity to start in a new, empty task
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Create the PendingIntent
            PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                    context, 0, notifyIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            builder.setContentIntent(notifyPendingIntent);
        }
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
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }
}
