package com.aprendendo.wearos_audio;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationService extends NotificationListenerService {

    private static final String TAG = "NotificationService";
    private TexttoSpeech ttsManager;

    @Override
    public void onCreate() {
        super.onCreate();
        ttsManager = new TexttoSpeech(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ttsManager.shutdown();
    }
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        String notificationText = sbn.getNotification().tickerText.toString();
        ttsManager.speak("Nova mensagem " + notificationText);
        Log.d(TAG, "Nova notificação: " + sbn.getPackageName());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        //  acessar informações sobre a notificação removida
        Log.d(TAG, "Notificação removida: " + sbn.getPackageName());
    }
}
