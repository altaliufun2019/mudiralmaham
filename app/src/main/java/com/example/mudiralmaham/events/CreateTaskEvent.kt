package com.example.mudiralmaham.events

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mudiralmaham.dataModels.Project
import com.example.mudiralmaham.dataModels.Task
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.utils.Database
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class CreateTaskEvent(name: String, comment: String, project: Project, owner: String, year: Int, month: Int, day: Int, hour: Int, minute: Int) {
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
        }
        else {
            task.isOver = true
            result = false
            reason = 1
        }
    }
}