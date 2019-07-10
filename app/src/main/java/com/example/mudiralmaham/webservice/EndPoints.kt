package com.example.mudiralmaham.webservice

import com.example.mudiralmaham.webservice.request.AddEventRequest
import com.example.mudiralmaham.webservice.request.GetEventRequest
import com.example.mudiralmaham.webservice.request.LoginRequest
import com.example.mudiralmaham.webservice.request.SignUpRequest
import com.example.mudiralmaham.webservice.response.LoginResponse
import com.example.mudiralmaham.webservice.response.SignUpResponse
import com.example.mudiralmaham.webservice.response.TaskResponse
import retrofit2.Call
import retrofit2.http.Body
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