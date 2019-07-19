package com.example.mudiralmaham.webservice.response

import com.google.gson.annotations.SerializedName
import java.util.*

class TaskResponse(
    @SerializedName("Name")
    var name: String,
    @SerializedName("Comment")
    var comment: String,
    @SerializedName("created_date")
    var createdDate: Date,
    @SerializedName("due_date")
    var dueDate: Date,
    @SerializedName("notification_date")
    var notificationDate: Date,
    @SerializedName("is_over")
    var isOver: Boolean,
    @SerializedName("is_done")
    var isDone: Boolean,
    @SerializedName("owner")
    var owner: String,
    @SerializedName("config")
    var config: String

)