package com.wk.weatherwise.data.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wk.weatherwise.R
import com.wk.weatherwise.ui.screens.main.MainActivity
import timber.log.Timber
import kotlin.random.Random

private const val TAG = "WeatherWiseFirebaseMess"
class WeatherWiseFirebaseMessageService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.tag(TAG).i("firebase token $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // Handle the received FCM message here
        Timber.tag(TAG).d("From: ${remoteMessage.from}")

        // Check if the message contains data
        if (remoteMessage.data.isNotEmpty()) {
            val title = this.getString(R.string.app_name)
            val notificationMessage = remoteMessage.data["Message"]
            NotificationHelper(this).showNotification(
                title,
                notificationMessage!!
            )
        }else {
            // Check if the message contains a notification payload
            remoteMessage.notification?.let {
                NotificationHelper(this).showNotification(
                    it.title!!,
                    it.body!!
                )
            }
        }
    }
}


class NotificationHelper(private val context: Context) {

    private val channelId = "weather_wise_notification_channel_01"
    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "weather_wise_notification_channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(title: String, message: String) {

        // Create an Intent for the activity you want to start.
        val resultIntent = Intent(context, MainActivity::class.java)

        // Create the TaskStackBuilder.
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            // Add the intent, which inflates the back stack.
            addNextIntentWithParentStack(resultIntent)
            // Get the PendingIntent containing the entire back stack.
            getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setContentIntent(resultPendingIntent)
            .setSmallIcon(R.drawable.weatherwise_logo) // Replace with your notification icon
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(Random.nextInt(), builder.build())
        }
    }
}