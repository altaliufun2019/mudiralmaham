package com.example.mudiralmaham.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationManagerCompat
import android.media.RingtoneManager
import android.media.Ringtone
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.example.mudiralmaham.AuthActivity
import com.example.mudiralmaham.dataModels.Task


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        createNotificationChannel(context!!)

//        val notificationManager: NotificationManager =
//            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val name = intent?.getStringExtra("name").toString()
        val comment = intent?.getStringExtra("comment").toString()
        val notification: Notification? = getNotification(name, comment, context)
        with(NotificationManagerCompat.from(context)) {
            notify(0, notification!!)
        }
    }


    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "task-notification"
            val descriptionText =""
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("taskNotification", name, importance).apply {
                description = descriptionText
                setShowBadge(true)
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getNotification(name: String, comment: String, context: Context): Notification {
        val intent = Intent(context, AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, "taskNotification")
            .setSmallIcon(com.example.mudiralmaham.R.drawable.task_name1)
            .setContentTitle("It's time to do task!!")
            .setContentText("${name} will be over in less than an hour...")
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, com.example.mudiralmaham.R.drawable.due_time))
            .setStyle(NotificationCompat.BigTextStyle().bigText(comment))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        return builder.build()
    }

}