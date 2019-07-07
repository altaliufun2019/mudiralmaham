package com.example.mudiralmaham.Webservice.Response

import com.google.gson.annotations.SerializedName
import java.util.*

class TaskResponse (
    @SerializedName("name") var name: String,
    @SerializedName("due_date") var date: Date,
    @SerializedName("description") var description: String,
    @SerializedName("notification_config") var notification_config: String,
    @SerializedName("content") var content: String,
    @SerializedName("owner") var owner: String,
    @SerializedName("created_date") var createdDate: Date
)