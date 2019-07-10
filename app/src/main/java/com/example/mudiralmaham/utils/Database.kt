package com.example.mudiralmaham.utils

import com.example.mudiralmaham.dataModels.DaoSession
import com.example.mudiralmaham.dataModels.Project
import com.example.mudiralmaham.dataModels.Task
import com.example.mudiralmaham.dataModels.TaskDao
import java.lang.Exception

object Database {
    fun getProjects(db: DaoSession = ContextHolder.mDaoSession): MutableList<Project> {
        try {
            return db.projectDao.loadAll()
        } catch (err: Exception) {
            return mutableListOf()
        }
    }

    fun getTasks(db: DaoSession = ContextHolder.mDaoSession): MutableList<Task> {
        try {
            return db.taskDao.loadAll()
        }catch (err: Exception) {
            return mutableListOf()
        }
    }

    fun getActiveTasks(db: DaoSession = ContextHolder.mDaoSession): MutableList<Task> {
        try {
            val qb = db.taskDao.queryBuilder()
            qb.where(TaskDao.Properties.IsOver.eq(false), TaskDao.Properties.IsDone.eq(false))
            return qb.list()
        } catch (err: Exception) {
            return mutableListOf()
        }
    }

    fun updateTask(task: Task, db: DaoSession = ContextHolder.mDaoSession): Boolean{
        try {
            db.taskDao.update(task)
            return true
        } catch (err: Exception) {
            return false
        }
    }

    fun deleteTask(task: Task, db: DaoSession = ContextHolder.mDaoSession): Boolean {
        try{
            db.taskDao.delete(task)
            return true
        } catch (err: Exception) {
            return false
        }
    }

    fun addTask(task: Task, db: DaoSession = ContextHolder.mDaoSession): Boolean {
        try {
            db.taskDao.insert(task)
            return true
        }catch (err: Exception) {}
        return false
    }
}