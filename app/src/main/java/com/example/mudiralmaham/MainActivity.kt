package com.example.mudiralmaham

import android.arch.persistence.room.Room
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.mudiralmaham.Database.Database
import com.example.mudiralmaham.pages.LoginFragment

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        initDb()
        setContentView(R.layout.main_activity)
        showLoginFragment()
    }

    private fun initDb() {
        Constants.database = Room
            .databaseBuilder(applicationContext, Database::class.java, Constants.databaseName)
            .build()
    }

    private fun showLoginFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val loginFragment = LoginFragment()
        fragmentTransaction.add(R.id.fragment_holder, loginFragment)
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