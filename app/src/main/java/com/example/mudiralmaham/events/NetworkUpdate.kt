package com.example.mudiralmaham.events

import com.example.mudiralmaham.dataModels.Project
import com.example.mudiralmaham.dataModels.Task
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.utils.Database
import com.example.mudiralmaham.webservice.request.GetProjectRequest
import com.example.mudiralmaham.webservice.request.GetTaskRequest
import com.example.mudiralmaham.webservice.response.ProjectResponse
import com.example.mudiralmaham.webservice.response.TaskResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkUpdate(networkProjects: List<ProjectResponse>) {
    init {
        var pId = 1L; var tId = 1L
        Database.deleteTaks()
        Database.deleteProjects()
        networkProjects.let {
            for (p in networkProjects) {
                val newProject = Project(pId, p.name, p.createdDate, p.description, p.collaborators)
                Database.addProject(newProject)
                ContextHolder.projects.add(newProject)
                pId++

                val data = GetTaskRequest(p.name)
                val request: Call<List<TaskResponse>> =
                    ContextHolder.webservice.getTask("Bearer ${ContextHolder.user?.token!!}", data)
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
                                ContextHolder.tasks.add(newTask)
                                tId++
                            }
                        }
                    }

                })

            }
            }
        }
}