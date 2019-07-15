package com.example.mudiralmaham.events

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mudiralmaham.dataModels.Task
import com.example.mudiralmaham.utils.Database
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import android.app.AlarmManager
import android.app.Notification
import android.os.SystemClock
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import com.example.mudiralmaham.AuthActivity
import com.example.mudiralmaham.services.DueTimeReceiver
import com.example.mudiralmaham.services.NotificationReceiver


@RequiresApi(Build.VERSION_CODES.O)
class CreateTaskEvent(
    name: String,
    comment: String,
    project: String,
    owner: String,
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    minute: Int,
    context: Context
) {
    var result: Boolean = false
    var reason: Int = 0

    init {
        val task = Task()
        task.name = name
        task.comment = comment
        task.owner = owner
        task.project = project

        val now = LocalDateTime.now()
        task.created_date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant())
        val cal = Calendar.getInstance()
        cal.clear()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, day)
        cal.set(Calendar.HOUR, hour)
        cal.set(Calendar.MINUTE, minute)
        task.due_date = cal.time
        cal.set(Calendar.HOUR, if (hour > 1) hour - 1 else 0)
        task.notification_date = cal.time

        if (task.due_date.after(task.created_date)) {
            task.isOver = false
            task.isDone = false
            result = Database.addTask(task)
            scheduleNotification(task, context)
        } else {
            task.isOver = true
            result = false
            reason = 1
        }
    }


    private fun scheduleNotification(task: Task, context: Context) {
//        val notification = getNotification(task, context)

        val notificationIntent = Intent(context, NotificationReceiver::class.java)
//        notificationIntent.putExtra("notification_id", 1)
//        notificationIntent.putExtra("notification", notification)
        notificationIntent.putExtra("name", task.name)
        notificationIntent.putExtra("comment", task.comment)
        val notifIntent =
            PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

//        val dueNotification = getDueNotification(task, context)
        val dueTimeIntent = Intent(context, DueTimeReceiver::class.java)
        dueTimeIntent.putExtra("notification_id", 1)
//        notificationIntent.putExtra("notification", dueNotification)
        dueTimeIntent.putExtra("name", task.name)
        dueTimeIntent.putExtra("comment", task.comment)
        val dueIntent =
            PendingIntent.getBroadcast(context, 0, dueTimeIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val futureInMillis = SystemClock.elapsedRealtime() - task.created_date.time

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        alarmManager!!.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            futureInMillis + task.notification_date.time,
            notifIntent
        )
        alarmManager!!.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            futureInMillis + task.due_date.time,
            dueIntent
        )
    }
//
//    private fun getNotification(task: Task, context: Context): Notification {
//        val intent = Intent(context, AuthActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
//
//        val builder = NotificationCompat.Builder(context, "taskNotification")
//            .setSmallIcon(com.example.mudiralmaham.R.drawable.task_name1)
//            .setContentTitle("It's time to do task!!")
//            .setContentText("${task.name} will be over in less than an hour...")
//            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//            .setLargeIcon(BitmapFactory.decodeResource(context.resources, com.example.mudiralmaham.R.drawable.due_time))
//            .setStyle(NotificationCompat.BigTextStyle().bigText(task.comment))
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//
//        return builder.build()
//    }
//
//
//    private fun getDueNotification(task: Task, context: Context): Notification {
//        val intent = Intent(context, AuthActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
//        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
//
//        val builder = NotificationCompat.Builder(context, "taskNotification")
//            .setSmallIcon(com.example.mudiralmaham.R.drawable.task_name1)
//            .setContentTitle("Task Due time!!")
//            .setContentText("${task.name} is over...")
//            .setLargeIcon(BitmapFactory.decodeResource(context.resources, com.example.mudiralmaham.R.drawable.due_time))
//            .setSound(sound)
//            .setStyle(NotificationCompat.BigTextStyle().bigText(task.comment))
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//
//        return builder.build()
//    }

}