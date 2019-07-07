package com.example.mudiralmaham.Webservice.Request

import com.google.gson.annotations.SerializedName

class SignUpRequest (
    @SerializedName("name") var name: String,
    @SerializedName("username") var username: String,
    @SerializedName("password") var password: String
)