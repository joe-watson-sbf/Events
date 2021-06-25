package com.mintsoftware.events.repository;

import com.mintsoftware.events.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    //public List<Event> getAllBy(int idInstructor);
}
