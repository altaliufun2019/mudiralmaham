package com.example.mudiralmaham.webservice.request

import com.google.gson.annotations.SerializedName

class SignUpRequest (
    @SerializedName("name") var name: String,
    @SerializedName("username") var username: String,
    @SerializedName("password") var password: String
)