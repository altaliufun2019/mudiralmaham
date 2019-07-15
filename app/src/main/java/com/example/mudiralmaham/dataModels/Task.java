package com.example.mudiralmaham.dataModels;

import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "task")
public class Task {
    @Id(autoincrement = true)
    private Long id;

    @Unique
    @NotNull
    private String name;

    private String comment;
    private String description;

    @NotNull
    private Date created_date;
    @NotNull
    private Date due_date;
    private Date notification_date;

    private boolean isDone;
    private boolean isOver;
//
    private String project;

    @NotNull
    private String owner;

    @Generated(hash = 476719917)
    public Task(Long id, @NotNull String name, String comment, String description,
            @NotNull Date created_date, @NotNull Date due_date,
            Date notification_date, boolean isDone, boolean isOver, String project,
            @NotNull String owner) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.description = description;
        this.created_date = created_date;
        this.due_date = due_date;
        this.notification_date = notification_date;
        this.isDone = isDone;
        this.isOver = isOver;
        this.project = project;
        this.owner = owner;
    }

    @Generated(hash = 733837707)
    public Task() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated_date() {
        return this.created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getDue_date() {
        return this.due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public Date getNotification_date() {
        return this.notification_date;
    }

    public void setNotification_date(Date notification_date) {
        this.notification_date = notification_date;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean getIsOver() {
        return this.isOver;
    }

    public void setIsOver(boolean isOver) {
        this.isOver = isOver;
    }

    public String getProject() {
        return this.project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
