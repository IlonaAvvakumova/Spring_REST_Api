package com.crud.service;

import com.crud.model.Event;
import com.crud.repository.JPA.EventRep;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventService {

    private final EventRep eventRep;


    @Autowired
    public EventService(EventRep eventRep) {
        this.eventRep = eventRep;
    }

    public List<Event> getAll() {
        return eventRep.findAll();
    }


    public Event getById(Integer id) {
       Optional<Event> optional =  eventRep.findById(id);
       return optional.orElse(null);
    }
}
