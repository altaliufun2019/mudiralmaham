package com.example.mudiralmaham.webservice.request

import com.google.gson.annotations.SerializedName

class GetTaskRequest (
    @SerializedName("project") var project: String? = null
)