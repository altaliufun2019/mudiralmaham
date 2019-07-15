package com.example.mudiralmaham.events

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mudiralmaham.dataModels.Task
import com.example.mudiralmaham.utils.Database
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import android.app.AlarmManager
import android.os.SystemClock
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.mudiralmaham.receivers.DueTimeReceiver
import com.example.mudiralmaham.receivers.NotificationReceiver


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
        val notificationIntent = Intent(context, NotificationReceiver::class.java)
        notificationIntent.putExtra("name", task.name)
        notificationIntent.putExtra("comment", task.comment)
        val notifIntent =
            PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val dueTimeIntent = Intent(context, DueTimeReceiver::class.java)
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

}