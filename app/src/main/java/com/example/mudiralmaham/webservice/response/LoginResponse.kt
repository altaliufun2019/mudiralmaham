package com.example.mudiralmaham.webservice.response

import com.google.gson.annotations.SerializedName

class LoginResponse (
    @SerializedName("token") var token: String,
    @SerializedName("msg") var msg: String,
    @SerializedName("name") var name: String
)