package com.crud.repository.JPA;
import com.crud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRep extends JpaRepository<User, Integer> {

}
