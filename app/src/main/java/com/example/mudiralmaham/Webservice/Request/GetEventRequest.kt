package com.example.mudiralmaham.Webservice.Request

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

class GetEventRequest (
    @SerializedName("username") var username: String
)