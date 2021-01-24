package com.example.laboratorio8;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DelayedMessageService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "com.example.laboratorio8.action.FOO";
    public static final String ACTION_BAZ = "com.example.laboratorio8.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "com.example.laboratorio8.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.example.laboratorio8.extra.PARAM2";

    public static final String EXTRA_MESSAGE= "message";
    public static final int NOTIFIACTION_ID= 12937;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    public DelayedMessageService() {
        super("DelayedMessageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this){
            try {
                wait(10000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        String text = intent.getStringExtra(EXTRA_MESSAGE);
        showText(text);

        /*if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }*/
    }
    private void showText(String text){
        Log.v("DelayedMessageService","The message is: "+ text);
        NotificationCompat.Builder build = new NotificationCompat.Builder(this )
                                            .setSmallIcon(R.drawable.ic_notification)
                                            .setContentTitle("Servicio")
                                            .setContentText("Notificacion desde el servicio")
                                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                                            .setVibrate(new long[] {0, 10000})
                                            .setAutoCancel(true);
        Intent actionIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        build.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // IMPORTANTE PARA VERSIONES POSTERIORES A ANDORID OREO
        //Se crea un canal de notificaciones con la misma importancia que el notification builder
        //y se le asigna un nombre y un ID, luego se integra en el gestor de notificaciones
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            build.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        //-----------------------

        notificationManager.notify(NOTIFIACTION_ID, build.build());
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}