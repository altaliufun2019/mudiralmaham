package com.example.mudiralmaham.Events

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mudiralmaham.DataModels.Task
import com.example.mudiralmaham.Utils.Database
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class CreateTaskEvent(name: String, comment: String, year: Int, month: Int, day: Int, hour: Int, minute: Int) {
    var result: Boolean = false

    init {
        val task = Task()
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
        task.name = name
        task.comment = comment
        result = Database.addTask(task)
    }
}