package com.mintsoftware.events.repository;

import com.mintsoftware.events.model.InstructorEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructorEventRepository extends JpaRepository<InstructorEvent, Integer> {
    List<InstructorEvent> findByIdInstructor(int idInstructor);
    void deleteInstructorEventByIdInstructorAndIdEvent(Integer idInstructor, Integer idEvent);
    void deleteInstructorEventByIdEvent(int idEvent);

}
