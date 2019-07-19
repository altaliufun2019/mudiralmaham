package com.example.mudiralmaham.webservice.response

import com.google.gson.annotations.SerializedName
import java.util.*

class ProjectResponse(
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("created_date") var createdDate: Date,
    @SerializedName("collaborators") var collaborators: String,
    @SerializedName("tasks") var tasks: List<TaskResponse>
)