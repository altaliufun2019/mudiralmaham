package com.example.mudiralmaham.utils

import android.annotation.SuppressLint
import android.os.Handler
import com.example.mudiralmaham.MainActivity
import com.example.mudiralmaham.dataModels.*
import com.example.mudiralmaham.webservice.EndPoints
import com.example.mudiralmaham.webservice.response.ProjectResponse

object ContextHolder {
    val databaseName: String = "makhzan"
    @SuppressLint("StaticFieldLeak")
    lateinit var dbHelper: DaoMaster.DevOpenHelper
    lateinit var mDaoSession: DaoSession
    lateinit var webservice: EndPoints
    var isNetworkConnected: Boolean = false
    @SuppressLint("StaticFieldLeak")
    var networkMonitor: MainActivity.ConnectionMonitor? = null

    lateinit var projects: MutableList<Project>
    lateinit var tasks: MutableList<Task>
    var user: User? = null

    fun getCacheData() {
        tasks = Database.getTasks()
        projects = Database.getProjects()
    }

    fun updateCacheFromNetwork(networkProjects: List<ProjectResponse>?) {
        projects = mutableListOf()
        var pId = 1L
        var tId = 1L
        Handler().post{
            Database.deleteTaks()
            Database.deleteProjects()
            for (p in networkProjects!!) {
                val newProject = Project(pId, p.name, p.createdDate, p.description, p.collaborators)
                Database.addProject(newProject)
                projects.add(newProject)
                pId++
                for (t in p.tasks) {
                    val newTask = Task(tId, t.name, t.comment, t.config, t.createdDate, t.dueDate, t.notificationDate, t.isDone, t.isOver, p.name, t.owner)
                    Database.addTask(newTask)
                    tasks.add(newTask)
                }
            }
        }
    }


//    TODO(to be replaced with Database method)
    fun getProjectTasks(projectName: String): MutableList<Task> {
        return Database.getProjectTasks(projectName)
    }

    fun updateTask(task: Task){
        Database.updateTask(task)
    }
}