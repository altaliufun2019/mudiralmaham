package com.example.mudiralmaham.Webservice.Response

import com.google.gson.annotations.SerializedName

class LoginResponse (
    @SerializedName("token") var token: String,
    @SerializedName("msg") var msg: String
)