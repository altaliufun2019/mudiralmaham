package com.example.mudiralmaham.utils

import android.annotation.SuppressLint
import android.os.Handler
import com.example.mudiralmaham.MainActivity
import com.example.mudiralmaham.dataModels.*
import com.example.mudiralmaham.events.NetworkUpdate
import com.example.mudiralmaham.webservice.EndPoints
import com.example.mudiralmaham.webservice.request.GetTaskRequest
import com.example.mudiralmaham.webservice.response.ProjectResponse
import com.example.mudiralmaham.webservice.response.TaskResponse
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        tasks = mutableListOf()
        EventBus.getDefault().post(networkProjects?.let { NetworkUpdate(it) })
    }


    //    TODO(to be replaced with Database method)
    fun getProjectTasks(projectName: String): MutableList<Task> {
        return Database.getProjectTasks(projectName)
    }

    fun updateTask(task: Task) {
        Database.updateTask(task)
    }
}