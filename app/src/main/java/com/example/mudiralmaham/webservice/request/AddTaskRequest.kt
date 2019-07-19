package com.example.mudiralmaham.webservice.request

import com.google.gson.annotations.SerializedName
import java.util.*

class AddTaskRequest (
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("comment")
    var comment: String? = null,
    @SerializedName("created_date")
    var created_date: Date? = null,
    @SerializedName("due_date")
    var due_date: Date? = null,
    @SerializedName("notification_date")
    var notification_date: Date? = null,
    @SerializedName("config")
    var config: String? = null,
    @SerializedName("owner")
    var owner: String? = null,
    @SerializedName("is_over")
    var isOver: Boolean? = false,
    @SerializedName("is_done")
    var isDone: Boolean? = false,
    @SerializedName("project")
    var project: String? = null
)