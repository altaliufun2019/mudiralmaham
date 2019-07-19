package com.example.mudiralmaham.utils

import com.example.mudiralmaham.dataModels.*
import com.example.mudiralmaham.events.TaskChange
import org.greenrobot.eventbus.EventBus
import java.lang.Exception

object Database {
    fun getProjects(db: DaoSession = ContextHolder.mDaoSession): MutableList<Project> {
        try {
            val qb = db.projectDao.queryBuilder()
            return qb.where(
                qb.or(
                    ProjectDao.Properties.Owners.like(
                        "%${ContextHolder.user?.email}%"
                    ), ProjectDao.Properties.Owners.like("%ALL%")
                )
            ).list()
        } catch (err: Exception) {
            return mutableListOf()
        }
    }

    fun getTasks(db: DaoSession = ContextHolder.mDaoSession): MutableList<Task> {
        try {
            return db.taskDao.queryBuilder().where(
                TaskDao.Properties.Owner.eq(
                    ContextHolder.user?.email
                )
            ).list()
        } catch (err: Exception) {
            return mutableListOf()
        }
    }

    fun getProjectTasks(project: String, db: DaoSession = ContextHolder.mDaoSession): MutableList<Task> {
        try {
            return db.taskDao.queryBuilder().where(TaskDao.Properties.Project.eq(project)).list()
        } catch (err: Exception) {
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

    fun updateTask(task: Task, db: DaoSession = ContextHolder.mDaoSession): Boolean {
        try {
            db.taskDao.update(task)
            return true
        } catch (err: Exception) {
            return false
        }
    }

    fun deleteTask(task: Task, db: DaoSession = ContextHolder.mDaoSession): Boolean {
        try {
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
        } catch (err: Exception) {
            return false
        }
    }

    fun addProject(project: Project, db: DaoSession = ContextHolder.mDaoSession): Boolean {
        try {
            db.projectDao.insert(project)
            return true
        } catch (err: Exception) {
        }
        return false
    }

    fun updateIsOverTask(name: String, dao: DaoSession = ContextHolder.mDaoSession) {
        try{
            var db = dao
            if (ContextHolder.mDaoSession != null)
                db = ContextHolder.mDaoSession
            var task = db.taskDao.queryBuilder().where(TaskDao.Properties.Name.eq(name)).list()[0]
            task.isOver = true
            db.taskDao.update(task)
            EventBus.getDefault().post(TaskChange())
        } catch (err: Exception) {

        }
    }

    fun deleteTaks(db: DaoSession = ContextHolder.mDaoSession) {
        db.taskDao.deleteAll()
    }

    fun deleteProjects(db: DaoSession = ContextHolder.mDaoSession) {
        db.projectDao.deleteAll()
    }


}