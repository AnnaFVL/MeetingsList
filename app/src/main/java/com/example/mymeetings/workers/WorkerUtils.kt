package com.example.mymeetings.workers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mymeetings.R

// Open code from developer.android.com

// Notification Channel constants
val NOTIFICATION_CHANNEL_NAME: CharSequence = "WorkManager Notifications about upcoming meeting"
const val NOTIFICATION_CHANNEL_DESCRIPTION = "Shows notifications before 1 hour of scheduled future meeting"
val NOTIFICATION_TITLE: CharSequence = "You have scheduled meetings in an hour..."
const val CHANNEL_ID = "MEETING_NOTIFICATION"
const val NOTIFICATION_ID = 1
@SuppressLint("MissingPermission")
fun makeReminderNotification(message: String, context: Context) {

    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = NOTIFICATION_CHANNEL_NAME
        val description = NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    // Create the notification
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.baseline_person_24)
        .setContentTitle(NOTIFICATION_TITLE)
        //.setContentText(message)
        .setStyle(NotificationCompat.BigTextStyle()
            .bigText(message))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show the notification
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}