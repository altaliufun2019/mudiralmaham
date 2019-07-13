package com.example.mudiralmaham.dataModels;

import org.greenrobot.greendao.annotation.*;

import java.util.Date;

@Entity(nameInDb = "project")
public class Project {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Unique
    private String name;

    @NotNull
    private Date created_date;

//    private Project parent;
    private String description;

    private String owners;

    @Generated(hash = 269448124)
    public Project(Long id, @NotNull String name, @NotNull Date created_date,
            String description, String owners) {
        this.id = id;
        this.name = name;
        this.created_date = created_date;
        this.description = description;
        this.owners = owners;
    }

    @Generated(hash = 1767516619)
    public Project() {
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

    public Date getCreated_date() {
        return this.created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwners() {
        return this.owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }
}
