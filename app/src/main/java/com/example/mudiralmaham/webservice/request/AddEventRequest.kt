package com.example.mudiralmaham.webservice.request

import com.google.gson.annotations.SerializedName

class AddEventRequest (
    @SerializedName("name") var name: String? = null,
    @SerializedName("created_date") var created_date: String? = null,
    @SerializedName("due_date") var due_date: String? = null,
    @SerializedName("content") var content: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("owner") var owner: String? = null,
    @SerializedName("notification_config") var notification_config: String? = null
)