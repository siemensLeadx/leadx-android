package com.siemens.leadx.utils.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.siemens.leadx.R
import com.siemens.leadx.data.local.entities.FireBaseToken
import com.siemens.leadx.data.local.sharedprefs.SharedPrefsUtils
import com.siemens.leadx.ui.splash.container.SplashActivity

class LeadXFireBaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        initAppNotifications(remoteMessage)
    }

    private fun initAppNotifications(remoteMessage: RemoteMessage) {
        val requestID = System.currentTimeMillis().toInt()
        val intent = Intent(this, SplashActivity::class.java)
        intent.putExtras(
            Bundle().also { bundle ->
                for (entry in remoteMessage.data.entries)
                    bundle.putString(entry.key, entry.value)
            }
        )
        val pendingIntent = PendingIntent.getActivity(
            this, requestID, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        sendNotification(
            remoteMessage.notification?.title,
            remoteMessage.notification?.body,
            pendingIntent,
            requestID
        )
    }

    override fun onNewToken(token: String) {
        SharedPrefsUtils.getInstance(this)
            .setFireBaseToken(FireBaseToken(doNeedToRegisterTokenServer = true, token = token))
    }

    private fun sendNotification(
        title: String?,
        content: String?,
        pendingIntent: PendingIntent,
        requestID: Int,
    ) {
        val channelId = getString(R.string.default_notification_channel_id)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setContentTitle(title)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(content)
            )
            .setContentText(content)
            .setAutoCancel(true)
            .setVisibility(VISIBILITY_PUBLIC)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                getString(R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(requestID, notificationBuilder.build())
    }
}
