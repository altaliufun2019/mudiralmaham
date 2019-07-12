package com.example.mudiralmaham.services

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import androidx.annotation.RequiresApi
import com.example.mudiralmaham.AuthActivity
import com.example.mudiralmaham.R
import com.example.mudiralmaham.dataModels.DaoMaster
import com.example.mudiralmaham.dataModels.DaoSession
import com.example.mudiralmaham.dataModels.Task
import com.example.mudiralmaham.dataModels.Project
import com.example.mudiralmaham.events.CreateTaskEvent
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.utils.Database
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class NotificationService: IntentService("NotificationService") {
    var tasks: MutableList<Task> = mutableListOf()
    var projects: MutableList<Project> = mutableListOf()
    var dbHelper: DaoMaster.DevOpenHelper? = null
    var mDaoSession: DaoSession? = null

    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)
        initDb()
        getCacheData()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private fun initDb() {
        dbHelper = DaoMaster.DevOpenHelper(this, ContextHolder.databaseName)
        mDaoSession = DaoMaster(dbHelper?.writableDb).newSession()
    }

    private fun getCacheData() {
        projects = Database.getProjects(mDaoSession!!)
        tasks = Database.getActiveTasks(mDaoSession!!)
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun taskCreated(createTaskEvent: CreateTaskEvent) {
        getCacheData()
    }

    private fun showNotification(task: Task) {
        createNotificationChannel()

        val intent = Intent(this, AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val builder = NotificationCompat.Builder(this, "taskNotification")
            .setSmallIcon(R.drawable.task_name1)
            .setContentTitle("task due time here!")
            .setContentText("${task.name} is no longer available...")
            .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.due_time))
            .setStyle(NotificationCompat.BigTextStyle().bigText(task.comment))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            notify(task.id.toInt(), builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "task-notification"
            val descriptionText =""
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("taskNotification", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onHandleIntent(intent: Intent?) {
        while(true) {
            val now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
            for (task in tasks) {
                if (task.due_date.before(now)) {
                    task.isOver = true
                    Database.updateTask(task, mDaoSession!!)
                    showNotification(task)
                }
            }
        }
    }

}