package com.example.mudiralmaham.webservice.request

import com.google.gson.annotations.SerializedName

class GetProjectRequest (
    @SerializedName("username") var username: String
)