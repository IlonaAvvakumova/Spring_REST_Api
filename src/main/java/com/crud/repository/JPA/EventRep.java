package com.crud.repository.JPA;
import com.crud.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRep extends JpaRepository<Event, Integer> {

}
