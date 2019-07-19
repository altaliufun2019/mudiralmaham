package com.example.mudiralmaham.utils

import android.annotation.SuppressLint
import android.os.Handler
import com.example.mudiralmaham.MainActivity
import com.example.mudiralmaham.dataModels.*
import com.example.mudiralmaham.webservice.EndPoints
import com.example.mudiralmaham.webservice.request.GetTaskRequest
import com.example.mudiralmaham.webservice.response.ProjectResponse
import com.example.mudiralmaham.webservice.response.TaskResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ContextHolder {
    val databaseName: String = "makhzan"
    @SuppressLint("StaticFieldLeak")
    lateinit var dbHelper: DaoMaster.DevOpenHelper
    lateinit var mDaoSession: DaoSession
    lateinit var webservice: EndPoints
    var isNetworkConnected: Boolean = false
    @SuppressLint("StaticFieldLeak")
    var networkMonitor: MainActivity.ConnectionMonitor? = null

    lateinit var projects: MutableList<Project>
    lateinit var tasks: MutableList<Task>
    var user: User? = null

    fun getCacheData() {
        tasks = Database.getTasks()
        projects = Database.getProjects()
    }

    fun updateCacheFromNetwork(networkProjects: List<ProjectResponse>?) {
        projects = mutableListOf()
        tasks = mutableListOf()
        var pId = 1L
        var tId = 1L
        Handler().post {
            Database.deleteTaks()
            Database.deleteProjects()
            networkProjects?.let {
                for (p in networkProjects) {
                    val newProject = Project(pId, p.name, p.createdDate, p.description, p.collaborators)
                    Database.addProject(newProject)
                    projects.add(newProject)
                    pId++

                    val data = GetTaskRequest(p.name)
                    val request: Call<List<TaskResponse>> = webservice.getTask("Bearer ${user?.token!!}", data)
                    request.enqueue(object : Callback<List<TaskResponse>> {
                        override fun onFailure(call: Call<List<TaskResponse>>, t: Throwable) {
                            print(t.message)
//                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onResponse(
                            call: Call<List<TaskResponse>>,
                            response: Response<List<TaskResponse>>
                        ) {
                            val taskres = response.body()
                            taskres?.let {
                                for (t in taskres) {
                                    val newTask = Task(
                                        tId,
                                        t.name,
                                        t.comment,
                                        t.config,
                                        t.createdDate,
                                        t.dueDate,
                                        t.notificationDate,
                                        t.isDone,
                                        t.isOver,
                                        t.project,
                                        t.owner
                                    )
                                    Database.addTask(newTask)
                                    tasks.add(newTask)
                                    tId ++
                                }
                            }
                        }

                    })


                }
            }
        }
    }


    //    TODO(to be replaced with Database method)
    fun getProjectTasks(projectName: String): MutableList<Task> {
        return Database.getProjectTasks(projectName)
    }

    fun updateTask(task: Task) {
        Database.updateTask(task)
    }
}