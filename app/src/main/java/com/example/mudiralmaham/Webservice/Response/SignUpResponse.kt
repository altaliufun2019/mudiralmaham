package com.example.mudiralmaham.Webservice.Response

import com.google.gson.annotations.SerializedName

class SignUpResponse (
    @SerializedName("token") var token: String,
    @SerializedName("msg") var msg: String
)