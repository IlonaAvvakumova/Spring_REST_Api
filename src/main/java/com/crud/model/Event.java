package com.crud.model;

import jakarta.persistence.*;

import lombok.*;
import com.fasterxml.jackson.annotation.*;



@Data
@Entity
@Table(name = "events", schema="flyway_db")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")

    @JsonBackReference
    User user;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinColumn(name = "file_id")
    FileEntity fileEntity;

    public Integer getId() {
        return id;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FileEntity getFileEntity() {
        return fileEntity;
    }

    public void setFileEntity(FileEntity fileEntity) {
        this.fileEntity = fileEntity;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", user=" + user.getName() +
                ", fileEntity=" + fileEntity.getName() +
                '}';
    }
}
