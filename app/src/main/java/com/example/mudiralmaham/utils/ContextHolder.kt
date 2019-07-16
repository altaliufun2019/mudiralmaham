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
//        fake data

//        tasks = mutableListOf()
//        projects = mutableListOf()
//
//        for (i in 1..10) {
//            var p = Project()
//            p.name = "Project Number $i"
//            p.created_date = Date()
//            projects.add(p)
//        }
//
//        for (i in 1..10) {
//            var t = Task()
//            t.name = "Task Number $i"
//            t.created_date = Date()
//            t.due_date = Date()
//            t.owner = "me"
//            t.project = "Project Number 1"
//            tasks.add(t)
//        }
    }

    fun getProjectTasks(projectName: String): MutableList<Task> {
        return Database.getProjectTasks(projectName)
//        fake data
//        val taskList = mutableListOf<Task>()
//        for (i in 1..10) {
//            var t = Task()
//            t.name = "Task Number $i from $projectName "
//            t.created_date = Date()
//            t.due_date = Date()
//            t.owner = "me"
//            t.project = "Project Number 1"
//            taskList.add(t)
//        }
//        return taskList
    }

    fun updateTask(task: Task){
        Database.updateTask(task)
    }
}