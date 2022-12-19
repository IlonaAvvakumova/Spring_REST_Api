package com.crud.service;

import com.crud.model.User;
import com.crud.repository.JPA.UserRep;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRep userRep;
@Autowired
    public UserService(UserRep userRep) {
        this.userRep = userRep;
    }

    public List<User> getAll() {
        return userRep.findAll();
    }


    public User getById(Integer id) {
      Optional<User> optional = userRep.findById(id);
        return optional.orElse(null);
    }


    public User create(User user) {
        return userRep.save(user);
    }


    public User update(User user) {
        return userRep.save(user);
    }


    public void deleteById(Integer id) {
        userRep.deleteById(id);
    }
}
