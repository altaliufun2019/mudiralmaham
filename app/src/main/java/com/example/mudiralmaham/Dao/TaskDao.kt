package com.example.mudiralmaham.Dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.mudiralmaham.DataModels.Task

@Dao
interface TaskDao {
    @Delete
    fun delete(task: Task)

    @Insert
    fun insertAll(vararg tasks: Task)

    @Query("SELECT * FROM task WHERE name=:name LIMIT 1")
    fun findByName(name: String): Task

    @Query("SELECT * FROM task WHERE uid=:id LIMIT 1")
    fun findById(id: Int): Task

    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Query("UPDATE task SET name=:name WHERE uid=:id")
    fun updateName(name: String, id: Int)

    @Query("UPDATE task SET project=:projectName WHERE uid=:id")
    fun updateProject(projectName: String, id: Int)
}