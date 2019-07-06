package com.example.mudiralmaham.DataModels

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity
data class Project (
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "priority") val priority: String,
    @ColumnInfo(name = "parent") val parent: String,
    @ColumnInfo(name = "collaborators") val collaborators: String
)