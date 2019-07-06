package com.example.mudiralmaham.Dao

import android.arch.persistence.room.*
import com.example.mudiralmaham.DataModels.Project
import com.example.mudiralmaham.DataModels.Task

@Dao
interface ProjectDao {
    @Delete
    fun delete(project: Project)

    @Insert
    fun insertAll(vararg projects: Project)

    @Query("SELECT * FROM project WHERE name=:name LIMIT 1")
    fun findByName(name: String): Project

    @Query("SELECT * FROM project WHERE uid=:id LIMIT 1")
    fun findById(id: Int): Project

    @Query("SELECT * FROM project")
    fun getAll(): List<Project>

    @Query("UPDATE project SET name=:name WHERE uid=:id")
    fun updateName(name: String, id: Int)

    @Query("UPDATE project SET parent=:parentName WHERE uid=:id")
    fun updateParent(parentName: String, id: Int)

    @Query("SELECT * FROM project WHERE name=:parentName LIMIT 1")
    fun getParent(parentName: String): Project

    @Query("SELECT * FROM project WHERE parent=:name")
    fun getChildren(name: String): List<Project>

    @Query("SELECT * FROM task WHERE project=:projectName")
    fun getTasks(projectName: String): List<Task>

}