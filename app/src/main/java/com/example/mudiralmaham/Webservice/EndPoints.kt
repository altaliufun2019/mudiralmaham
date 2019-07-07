package com.example.mudiralmaham.Webservice

import com.example.mudiralmaham.Webservice.Request.AddEventRequest
import com.example.mudiralmaham.Webservice.Request.GetEventRequest
import com.example.mudiralmaham.Webservice.Request.LoginRequest
import com.example.mudiralmaham.Webservice.Request.SignUpRequest
import com.example.mudiralmaham.Webservice.Response.LoginResponse
import com.example.mudiralmaham.Webservice.Response.SignUpResponse
import com.example.mudiralmaham.Webservice.Response.TaskResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.Headers
import retrofit2.http.POST

interface EndPoints {
    @POST("/auth/signUp")
    fun signUp(@Body data: SignUpRequest): Call<SignUpResponse>

    @POST("/auth/login")
    fun login(@Body data: LoginRequest): Call<LoginResponse>

    @POST("/event/get")
    fun getEvents(@Body data: GetEventRequest): Call<List<TaskResponse>>

    @POST("/event/get")
    fun addEvents(@Body data: AddEventRequest)
}