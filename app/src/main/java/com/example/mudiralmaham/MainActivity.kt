package com.example.mudiralmaham

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mudiralmaham.DataModels.DaoMaster
import com.example.mudiralmaham.Pages.LoginFragment
import com.example.mudiralmaham.Pages.TaskCreationFragment
import com.example.mudiralmaham.Utils.ContextHolder
import com.example.mudiralmaham.Utils.Database
import com.example.mudiralmaham.Webservice.EndPoints
import com.example.mudiralmaham.Webservice.RetrofitInstance
import org.greenrobot.eventbus.EventBus

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initWebservice()
        initDb()
        getCacheData()
        showLoginFragment()
    }

    private fun getCacheData() {
        ContextHolder.projects = Database.getProjects()
        ContextHolder.tasks = Database.getTasks()
    }

    private fun initWebservice() {
        ContextHolder.webservice = RetrofitInstance.retrofit?.create(EndPoints::class.java)!!
    }

    private fun initDb() {
        ContextHolder.dbHelper = DaoMaster.DevOpenHelper(this, ContextHolder.databaseName)
        ContextHolder.mDaoSession = DaoMaster(ContextHolder.dbHelper.writableDb).newSession()
    }

    private fun showLoginFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val loginFragment = LoginFragment()
//        fragmentTransaction.replace(R.id.fragment_holder, TaskCreationFragment())
        fragmentTransaction.replace(R.id.fragment_holder, loginFragment).disallowAddToBackStack()
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
        getCacheData()

    }
}