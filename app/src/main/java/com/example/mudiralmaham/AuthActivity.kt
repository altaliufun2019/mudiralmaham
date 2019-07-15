package com.example.mudiralmaham

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.mudiralmaham.dataModels.DaoMaster
import com.example.mudiralmaham.dataModels.Project
import com.example.mudiralmaham.dataModels.User
import com.example.mudiralmaham.pages.LoginFragment
import com.example.mudiralmaham.services.NotificationService
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.utils.Database
import com.example.mudiralmaham.webservice.EndPoints
import com.example.mudiralmaham.webservice.RetrofitInstance
import java.util.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)
        initWebservice()
        initDb()

//        Database.addProject(
//            Project(
//                1,
//                "project1",
//                Date(234987),
//                "project 1 desc",
//                "asdf@asdf.asdf __ majidstic@gmail.com")
//        )
//        Database.addProject(
//            Project(
//                2,
//                "project2",
//                Date(2349877),
//                "project 2 desc",
//                "asdf@asdf.asdf __ majidstic@gmail.com __ amoo@yahoo.com")
//        )
//        Database.addProject(
//            Project(
//                3,
//                "project3",
//                Date(23498987),
//                "project 3 desc",
//                "asdf@asdf.asdf __ majidstic@gmail.com __ abas@gh.gh __ gh@gh.gh")
//        )
        getCacheData()
        Intent(this, NotificationService::class.java).also { intent ->

            startService(intent) }
        if (ContextHolder.user == null)
            showFragment(LoginFragment())
        else
            startActivity(Intent(applicationContext, MainActivity::class.java))
    }

    private fun getCacheData() {
        val email = getPreferences(Context.MODE_PRIVATE).getString("mudir_email", "")
        val name = getPreferences(Context.MODE_PRIVATE).getString("mudir_name", "")
        if (email?.equals("")!!)
            return
        ContextHolder.user = User(name, email)
        ContextHolder.getCacheData()
    }

    private fun initWebservice() {
        ContextHolder.webservice = RetrofitInstance.retrofit?.create(EndPoints::class.java)!!
    }

    private fun initDb() {
        ContextHolder.dbHelper = DaoMaster.DevOpenHelper(this, ContextHolder.databaseName)
        ContextHolder.mDaoSession = DaoMaster(ContextHolder.dbHelper.writableDb).newSession()


    }

    private fun showFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_holder, fragment).disallowAddToBackStack()
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