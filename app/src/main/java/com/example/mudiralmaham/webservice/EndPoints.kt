package com.example.mudiralmaham.webservice

import com.example.mudiralmaham.webservice.request.*
import com.example.mudiralmaham.webservice.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface EndPoints {
    @POST("/auth/signUp")
    fun signUp(@Body data: SignUpRequest): Call<SignUpResponse>

    @POST("/auth/login")
    fun login(@Body data: LoginRequest): Call<LoginResponse>

    @POST("/project/add")
    fun addProject(@Header("Authorization") token: String, @Body data: AddProjectRequest): Call<AddResponse>

    @POST("/project/get")
    fun getProjects(@Header("Authorization") token: String, @Body data: GetProjectRequest): Call<List<ProjectResponse>>

    @POST("/project/add_collaborator")
    fun addCollaborator(@Header("Authorization") token: String, @Body data: AddCollaboratorRequest): Call<AddResponse>

    @POST("/task/add")
    fun addTask(@Header("Authorization") token: String, @Body data: AddTaskRequest): Call<AddResponse>

    @POST("/task/get")
    fun getTask(@Header("Authorization") token: String, @Body data: GetTaskRequest): Call<List<TaskResponse>>

    @POST("/task/update")
    fun updateTask(@Header("Authorization") token: String, @Body data: AddTaskRequest): Call<AddResponse>
}