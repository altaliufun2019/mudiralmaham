package com.example.mudiralmaham.webservice.request

import com.google.gson.annotations.SerializedName

class GetEventRequest (
    @SerializedName("username") var username: String
)