package com.example.mudiralmaham.Utils

import com.example.mudiralmaham.DataModels.Project
import com.example.mudiralmaham.DataModels.Task
import java.lang.Exception

object Database {
    fun getProjects(): MutableList<Project> {
        return ContextHolder.mDaoSession.projectDao.loadAll()
    }

    fun getTasks(): MutableList<Task> {
        return ContextHolder.mDaoSession.taskDao.loadAll()
    }

    fun addTask(task: Task): Boolean {
        try {
            ContextHolder.mDaoSession.taskDao.insert(task)
            return true
        }catch (err: Exception) {}
        return false
    }
}