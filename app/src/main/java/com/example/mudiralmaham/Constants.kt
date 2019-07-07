package com.example.mudiralmaham

import com.example.mudiralmaham.Database.Database
import com.example.mudiralmaham.Webservice.EndPoints

object Constants {
    val databaseName: String = "makhzan"
    lateinit var database: Database
    lateinit var webservice: EndPoints
}