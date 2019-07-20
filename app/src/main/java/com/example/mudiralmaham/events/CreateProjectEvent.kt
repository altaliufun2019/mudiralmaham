package com.example.mudiralmaham.events

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.mudiralmaham.dataModels.Project
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.utils.Database
import com.example.mudiralmaham.webservice.request.AddProjectRequest
import com.example.mudiralmaham.webservice.response.AddResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class CreateProjectEvent(name: String, description: String, collaborators: String, context: Context) {
    var result: Int = 0
    lateinit var project: Project

    init {
        project = Project()
        project.name = name; project.description = description; project.owners =
        if(!collaborators.equals("")) "${ContextHolder.user?.email} __ $collaborators" else ContextHolder.user?.email
        val now = LocalDateTime.now()
        project.created_date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant())

        val data = AddProjectRequest(project.name, project.created_date, project.description, project.owners)
        if (ContextHolder.isNetworkConnected) {
            val request: Call<AddResponse> = ContextHolder.webservice.addProject("Bearer ${ContextHolder.user?.token}", data)
            request.enqueue(object : Callback<AddResponse> {
                override fun onFailure(call: Call<AddResponse>, t: Throwable) {
                    Toast.makeText(context, "couldn't sync with server", Toast.LENGTH_SHORT).show()
                    ContextHolder.networkMonitor?.projectSyncQueue?.add(data)
                }

                override fun onResponse(call: Call<AddResponse>, response: Response<AddResponse>) {

                }
            })
        } else {
            ContextHolder.networkMonitor?.projectSyncQueue?.add(data)
        }
        if (!Database.addProject(project))
            result = 1
    }
}
