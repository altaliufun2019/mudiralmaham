package com.example.mudiralmaham.webservice.request

import com.google.gson.annotations.SerializedName
import java.util.*

class AddProjectRequest (
    @SerializedName("name") var name: String? = null,
    @SerializedName("created_date") var created_date: Date? = null,
//    @SerializedName("due_date") var due_date: String? = null,
//    @SerializedName("content") var content: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("collaborators") var collaborators: String? = null
//    @SerializedName("owner") var owner: String? = null,
//    @SerializedName("notification_config") var notification_config: String? = null
)