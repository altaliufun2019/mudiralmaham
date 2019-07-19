package com.example.mudiralmaham.webservice.request

import com.google.gson.annotations.SerializedName

class AddCollaboratorRequest (
    @SerializedName("username") var username: String? = null,
    @SerializedName("token") var token: String? = null,
    @SerializedName("project") var project: String? = null
)