package com.example.mudiralmaham.Utils

import com.example.mudiralmaham.DataModels.Project
import com.example.mudiralmaham.DataModels.Task

object Database {
    fun getProjects(): MutableList<Project> {
        return ContextHolder.mDaoSession.projectDao.loadAll()
    }

    fun getTasks(): MutableList<Task> {
        return ContextHolder.mDaoSession.taskDao.loadAll()
    }
}