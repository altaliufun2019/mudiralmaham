package com.example.mudiralmaham.webservice.request

import com.google.gson.annotations.SerializedName

class LoginRequest (
    @SerializedName("username") var username: String,
    @SerializedName("password") var password: String
)