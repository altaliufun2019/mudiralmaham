package com.example.mudiralmaham.utils

import android.annotation.SuppressLint
import com.example.mudiralmaham.dataModels.*
import com.example.mudiralmaham.webservice.EndPoints

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
        projects = Database.getProjects()
        tasks = Database.getTasks()
    }
}