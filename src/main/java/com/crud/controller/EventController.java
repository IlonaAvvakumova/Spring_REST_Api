package com.crud.controller;

import com.crud.model.Event;
import com.crud.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    ObjectMapper objectMapper;

    String jsonString;

    @GetMapping("/{id}")
    public void getById(@PathVariable("id") int id) throws  IOException {
        Event event = eventService.getById(id);
        objectMapper.writeValueAsString(event);
    }

    @GetMapping
    public void getAll() throws JsonProcessingException {
        List<Event> eventEntities = eventService.getAll();
        for (Event u : eventEntities
        ) {
            jsonString = objectMapper.writeValueAsString(u);
        }
    }
}

