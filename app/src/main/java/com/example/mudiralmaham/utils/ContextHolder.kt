package com.example.mudiralmaham.utils

import android.annotation.SuppressLint
import com.example.mudiralmaham.dataModels.*
import com.example.mudiralmaham.webservice.EndPoints
import java.util.*

object ContextHolder {
    val databaseName: String = "makhzan"
    @SuppressLint("StaticFieldLeak")
    lateinit var dbHelper: DaoMaster.DevOpenHelper
    lateinit var mDaoSession: DaoSession
    lateinit var webservice: EndPoints

    lateinit var projects: MutableList<Project>
    lateinit var tasks: MutableList<Task>
    var user: User? = null

    fun getCacheData() {
        tasks = Database.getTasks()
        projects = Database.getProjects()
    }

    fun getProjectTasks(projectName: String): MutableList<Task> {
        return Database.getProjectTasks(projectName)
    }

    fun updateTask(task: Task){
        Database.updateTask(task)
    }
}