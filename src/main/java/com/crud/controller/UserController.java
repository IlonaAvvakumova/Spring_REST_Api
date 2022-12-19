package com.crud.controller;

import com.crud.model.User;
import com.crud.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/users/*")
public class UserController extends HttpServlet {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    ObjectMapper objectMapper;

    String jsonString;

    @GetMapping("/{id}")
    public void getById(@PathVariable("id") int id) throws IOException {
        User byId = userService.getById(id);
        String jsonString = objectMapper.writeValueAsString(byId);
    }

    @GetMapping
    public void getAll() throws JsonProcessingException {
        List<User> userEntities = userService.getAll();
        for (User u : userEntities
        ) {
            jsonString = objectMapper.writeValueAsString(u);
        }
    }


    @PostMapping("/create")
    @ResponseBody
    protected void create(@RequestBody User user) throws IOException {

      /*      String json = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            User users = new ObjectMapper().readValue(json, User.class);*/
            userService.create(user);
    }

    @PutMapping ("/update")
    @ResponseBody
    protected void update(@RequestBody User user) throws IOException {
     /*   if (req.getRequestURI().equals("/REST_API_App/api/v1/users/update")) {
            String json = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            User user = new ObjectMapper().readValue(json, User.class);*/
            userService.update(user);

    }

    @DeleteMapping("/{id}")
    protected void delete(@PathVariable("id") int id) {

            userService.deleteById(id);

    }
}
