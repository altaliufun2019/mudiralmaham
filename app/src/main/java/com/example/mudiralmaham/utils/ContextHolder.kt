package com.example.mudiralmaham.utils

import android.annotation.SuppressLint
import com.example.mudiralmaham.dataModels.DaoMaster
import com.example.mudiralmaham.dataModels.DaoSession
import com.example.mudiralmaham.dataModels.Project
import com.example.mudiralmaham.dataModels.Task
import com.example.mudiralmaham.webservice.EndPoints

object ContextHolder {
    val databaseName: String = "makhzan"
    @SuppressLint("StaticFieldLeak")
    lateinit var dbHelper: DaoMaster.DevOpenHelper
    lateinit var mDaoSession: DaoSession
    lateinit var webservice: EndPoints
    lateinit var projects: MutableList<Project>
    lateinit var tasks: MutableList<Task>
}