package com.example.mudiralmaham

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.mudiralmaham.dataModels.DaoMaster
import com.example.mudiralmaham.dataModels.Project
import com.example.mudiralmaham.dataModels.User
import com.example.mudiralmaham.pages.LoginFragment
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.utils.Database
import com.example.mudiralmaham.webservice.EndPoints
import com.example.mudiralmaham.webservice.RetrofitInstance
import com.example.mudiralmaham.webservice.request.GetProjectRequest
import com.example.mudiralmaham.webservice.response.LoginResponse
import com.example.mudiralmaham.webservice.response.ProjectResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)
        initWebservice()
        initDb()
        Handler().post {
            insertDefaultProjects()
        }


        ContextHolder.networkMonitor = MainActivity.ConnectionMonitor(
            this
        )
        getCacheData()
        if (ContextHolder.user == null)
            showFragment(LoginFragment())
        else
            startActivity(Intent(applicationContext, MainActivity::class.java))
    }

    private fun getCacheData() {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val email = sharedPreferences.getString("mudir_email", "")
        val token = sharedPreferences.getString("mudir_token", "")
        val name = sharedPreferences.getString("mudir_name", "")
//        val email = getPreferences(Context.MODE_PRIVATE).getString("mudir_email", "")
//        val token = getPreferences(Context.MODE_PRIVATE).getString("mudir_token", "")
//        val name = getPreferences(Context.MODE_PRIVATE).getString("mudir_name", "")
        if (token?.equals("")!!) {
            ContextHolder.user = null
            return
        }
        if (ContextHolder.isNetworkConnected) {
            ContextHolder.user = User(name, email, token)
            val data = GetProjectRequest(email!!)
            val request: Call<List<ProjectResponse>> = ContextHolder.webservice.getProjects("Bearer $token", data)
            request.enqueue(object : Callback<List<ProjectResponse>> {
                override fun onResponse(call: Call<List<ProjectResponse>>, response: Response<List<ProjectResponse>>) {
                    if (response.code() == 200)
                        ContextHolder.updateCacheFromNetwork(response.body())
                    else
                        Toast.makeText(
                            applicationContext,
                            "Cannot retrieve data from server",
                            Toast.LENGTH_SHORT
                        ).show()
                }

                override fun onFailure(call: Call<List<ProjectResponse>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Cannot retrieve data from server, check credentials", Toast.LENGTH_SHORT).show()
                }
            })
        } else
            ContextHolder.getCacheData()
    }

    private fun initWebservice() {
        ContextHolder.webservice = RetrofitInstance.retrofit?.create(EndPoints::class.java)!!
    }

    private fun initDb() {
        ContextHolder.dbHelper = DaoMaster.DevOpenHelper(this, ContextHolder.databaseName)
        ContextHolder.mDaoSession = DaoMaster(ContextHolder.dbHelper.writableDb).newSession()
    }

    private fun insertDefaultProjects() {
//        Database.addProject(
//            Project(
//                4,
//                "TODAY",
//                Date(234987),
//                "today jobs",
//                "ALL"
//            )
//        )
//        Database.addProject(
//            Project(
//                5,
//                "TOMORROW",
//                Date(2349877),
//                "tomorrow jobs",
//                "ALL"
//            )
//        )
//        Database.addProject(
//            Project(
//                6,
//                "NEXTWEEK",
//                Date(23498987),
//                "this week jobs",
//                "ALL"
//            )
//        )
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_holder, fragment).disallowAddToBackStack()
        fragmentTransaction.commit()

    }

    override fun onDestroy() {
        super.onDestroy()
        ContextHolder.networkMonitor?.disable()
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