package com.example.mudiralmaham.utils

import com.example.mudiralmaham.dataModels.*
import java.lang.Exception

object Database {
    fun getProjects(db: DaoSession = ContextHolder.mDaoSession): MutableList<Project> {
        try {
            return db.projectDao.queryBuilder().where(
                ProjectDao.Properties.Owners.like(
                    "%${ContextHolder.user?.email}%"
                )).list()
        } catch (err: Exception) {
            return mutableListOf()
        }
    }

    fun getTasks(db: DaoSession = ContextHolder.mDaoSession): MutableList<Task> {
        try {
            return db.taskDao.queryBuilder().where(
                TaskDao.Properties.Owner.eq(
                    ContextHolder.user?.email
                )).list()
        }catch (err: Exception) {
            return mutableListOf()
        }
    }

    fun getProjectTasks(project:String, db: DaoSession = ContextHolder.mDaoSession): MutableList<Task> {
        try {
            return db.taskDao.queryBuilder().where(TaskDao.Properties.Project.eq(project)).list()
        }catch (err: Exception) {
            return mutableListOf()
        }
    }


    fun getActiveTasks(db: DaoSession = ContextHolder.mDaoSession): MutableList<Task> {
        try {
            val qb = db.taskDao.queryBuilder()
            qb.where(
                TaskDao.Properties.IsOver.eq(false),
                TaskDao.Properties.IsDone.eq(false),
                TaskDao.Properties.Owner.eq(ContextHolder.user?.email)
            )
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

    fun addProject(project: Project, db: DaoSession = ContextHolder.mDaoSession): Boolean {
        try {
            db.projectDao.insert(project)
            return true
        }catch (err: Exception) {}
        return false
    }



}