package com.example.mudiralmaham.DataModels

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(foreignKeys = arrayOf(ForeignKey(
    entity = Project::class,
    parentColumns = arrayOf("name"),
    childColumns = arrayOf("project")
)))
data class Task (
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "due_date") val due_date: Date,
    @ColumnInfo(name = "project") val project: String
)