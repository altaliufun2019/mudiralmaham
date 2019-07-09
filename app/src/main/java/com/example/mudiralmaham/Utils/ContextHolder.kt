package com.example.mudiralmaham.Utils

import android.annotation.SuppressLint
import com.example.mudiralmaham.DataModels.DaoMaster
import com.example.mudiralmaham.DataModels.DaoSession
import com.example.mudiralmaham.DataModels.Project
import com.example.mudiralmaham.DataModels.Task
import com.example.mudiralmaham.Webservice.EndPoints

object ContextHolder {
    val databaseName: String = "makhzan"
    @SuppressLint("StaticFieldLeak")
    lateinit var dbHelper: DaoMaster.DevOpenHelper
    lateinit var mDaoSession: DaoSession
    lateinit var webservice: EndPoints
    lateinit var projects: MutableList<Project>
    lateinit var tasks: MutableList<Task>
}