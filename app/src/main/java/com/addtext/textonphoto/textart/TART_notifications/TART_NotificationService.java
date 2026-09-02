package com.addtext.textonphoto.textart.TART_notifications;

import android.content.Context;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationReceivedEvent;
import com.onesignal.OneSignal;

public class TART_NotificationService implements OneSignal.OSRemoteNotificationReceivedHandler {

    @Override
    public void remoteNotificationReceived(Context context, OSNotificationReceivedEvent osNotificationReceivedEvent) {
        OSNotification notification = osNotificationReceivedEvent.getNotification();
        
        String title = notification.getTitle();
        String body = notification.getBody();
        
        Log.e("NOTIFICATION_LOG", "Received: " + title + " - " + body);
        
        if (title == null) title = "Text Art";
        if (body == null) body = "New notification";
        
        TART_NotificationDB db = new TART_NotificationDB(context);
        db.insertNotification(title, body, System.currentTimeMillis());
        
        // Complete with null means don't display it, but we DO want to display it.
        osNotificationReceivedEvent.complete(notification);
    }
}
