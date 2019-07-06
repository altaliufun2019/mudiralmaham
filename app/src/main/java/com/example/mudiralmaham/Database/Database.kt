package com.example.mudiralmaham.Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.mudiralmaham.Dao.ProjectDao
import com.example.mudiralmaham.Dao.TaskDao
import com.example.mudiralmaham.DataModels.Project
import com.example.mudiralmaham.DataModels.Task

@Database(entities = [Task::class, Project::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun projectDao(): ProjectDao
}