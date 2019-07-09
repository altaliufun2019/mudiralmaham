package com.example.mudiralmaham

import android.annotation.SuppressLint
import com.example.mudiralmaham.DataModels.DaoMaster
import com.example.mudiralmaham.DataModels.DaoSession
import com.example.mudiralmaham.Webservice.EndPoints

object Constants {
    val databaseName: String = "makhzan"
    @SuppressLint("StaticFieldLeak")
    lateinit var dbHelper: DaoMaster.DevOpenHelper
    lateinit var mDaoSession: DaoSession
    lateinit var webservice: EndPoints
}