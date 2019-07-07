package com.example.mudiralmaham

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mudiralmaham.Database.Database
import com.example.mudiralmaham.Pages.LoginFragment
import com.example.mudiralmaham.Webservice.EndPoints
import com.example.mudiralmaham.Webservice.RetrofitInstance

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initWebservice()
//        initDb()
//        Constants.database
        showLoginFragment()

    }

    private fun initWebservice() {
        Constants.webservice = RetrofitInstance.retrofit?.create(EndPoints::class.java)!!
    }

    private fun initDb() {
        Constants.database = Room
            .databaseBuilder(applicationContext, Database::class.java, Constants.databaseName)
            .build()
    }

    private fun showLoginFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val loginFragment = LoginFragment()
        fragmentTransaction.replace(R.id.fragment_holder, loginFragment)
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onStart() {
        super.onStart()
    }
}