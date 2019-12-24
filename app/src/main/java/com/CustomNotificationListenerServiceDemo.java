package com;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

public class CustomNotificationListenerServiceDemo extends NotificationListenerService {

    // 在收到消息时触发
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (!"com.tencent.mm".equals(sbn.getPackageName()))
            return;
        Notification notification = sbn.getNotification();
        if (notification == null)
            return;
        PendingIntent pendingIntent = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Bundle extras = notification.extras;
            if (extras != null) {
                String title = extras.getString(Notification.EXTRA_TITLE);
                String content = extras.getString(Notification.EXTRA_TEXT);
                if (!TextUtils.isEmpty(content) && content.contains("[微信红包]"))
                {
                    pendingIntent = notification.contentIntent;
                }
            }
        }
        try{
            if (pendingIntent!=null){
                pendingIntent.send();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
//        Bundle extras = sbn.getNotification().extras;
//        // 获取接收消息APP的包名
//        String notificationPkg = sbn.getPackageName();
//        // 获取接收消息的抬头
//        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
//        // 获取接收消息的内容
//        String notificationText = extras.getString(Notification.EXTRA_TEXT);
//
//        Log.i("XSL_Test", "Notification posted " + notificationTitle + " & " + notificationText);
    }

    // 在删除消息时触发
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);
        Log.i("XSL_Test", "Notification removed " + notificationTitle + " & " + notificationText);
    }
}
