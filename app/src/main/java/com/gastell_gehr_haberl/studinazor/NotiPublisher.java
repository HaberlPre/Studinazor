package com.gastell_gehr_haberl.studinazor;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by lucas on 21.09.2017.
 */

public class NotiPublisher extends BroadcastReceiver {

    //Statische Variablen
    public static String NOTI_ID = "notification-id";
    public static String NOTI = "notification";

    /**
     * Löst die Notification aus
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notiMan = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification noti = intent.getParcelableExtra(NOTI);
        int id = intent.getIntExtra(NOTI_ID, 0);
        notiMan.notify(id, noti);
    }
}
