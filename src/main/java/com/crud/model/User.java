package com.crud.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users", schema="flyway_db")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Event.class)
    List<Event> eventEntities;

    public Integer getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEventEntities() {
        return eventEntities;
    }

    public void setEventEntities(List<Event> eventEntities) {
        this.eventEntities = eventEntities;
    }
}
