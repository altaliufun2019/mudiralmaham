package com.example.mudiralmaham.events

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mudiralmaham.dataModels.Project
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.utils.Database
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class CreateProjectEvent(name: String, description: String, collaborators: String) {
    var result: Int = 0
    init {
        val project = Project()
        project.name = name; project.description = description; project.owners = "${ContextHolder.user?.email} __ $collaborators"
        val now = LocalDateTime.now()
        project.created_date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant())
        if (!Database.addProject(project))
            result = 1
    }
}
