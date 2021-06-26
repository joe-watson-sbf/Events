package com.mintsoftware.events.dao;

import com.mintsoftware.events.dto.EventDTO;
import com.mintsoftware.events.dto.InstructorDTO;
import com.mintsoftware.events.model.Event;
import com.mintsoftware.events.model.Instructor;
import com.mintsoftware.events.model.InstructorEvent;
import com.mintsoftware.events.model.businessException.BusinessException;
import com.mintsoftware.events.repository.EventRepository;
import com.mintsoftware.events.repository.InstructorEventRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppDAO {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    InstructorEventRepository instructorEventRepository;

    /**
    * CRUD EVENT ::: Using MySQL for persistence
    **/
    public void create(EventDTO eventDTO){
        eventRepository.save(dtoToEntity(eventDTO));
    }

    public void saveEventList(List<EventDTO> eventDTOS){
        List<Event> list = dtosEventToEntities(eventDTOS);
        eventRepository.saveAll(list);
    }

    public boolean exists(int id){
        return eventRepository.existsById(id);
    }
    public void delete(EventDTO eventDTO){
        deleteInstructorEvents(eventDTO.getId());
        eventRepository.delete(dtoToEntity(eventDTO));

    }

    public EventDTO getEventById(Integer id){
        return entityToDto(eventRepository.getById(id));
    }

    public List<EventDTO> findAllEvent(){
        return eventRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * INTERSECTION TABLE EVENT and INSTRUCTOR ::: Using MySQL for persistence
     **/

    public boolean saveInstructorEvents(int idInstructor, int idEvent){
        Event event = eventRepository.getById(idEvent);

        if(event.isEnable()){
            event.setEnable(false);
            eventRepository.save(event);
            InstructorEvent instructorEvent = new InstructorEvent();
            instructorEvent.setIdInstructor(idInstructor);
            instructorEvent.setIdEvent(idEvent);
            instructorEventRepository.save(instructorEvent);
            return true;
        }
        return false;
    }

    public void deleteInstructorEvents(int idEvent){
        instructorEventRepository
                .findAll()
                .stream()
                .filter(e -> e.getIdEvent() == idEvent)
                .findFirst().ifPresent(instructorEvent -> {
                    instructorEventRepository.delete(instructorEvent);
                    Event event = eventRepository.getById(idEvent);
                    event.setEnable(true);
                    eventRepository.save(event);
                });
    }

    private List<EventDTO> getInstructorEvents(int idInstructor){
        List<InstructorEvent> instructorEvents = instructorEventRepository.findByIdInstructor(idInstructor);

        return instructorEvents
                .stream()
                .map(instructorEvent -> eventRepository.getById(instructorEvent.getIdEvent()))
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     *  Sample Data Instructor
     * **/

    public List<InstructorDTO> findAllInstructor(){
        List<Instructor> list = List.of(
                new Instructor(1, "Jacob", "Smith", "08/02/1993"),
                new Instructor(2, "Brandon", "Johnson", "25/12/1990"),
                new Instructor(3, "Adrian", "Robinson", "11/05/1987"),
                new Instructor(4, "Amy", "Scott", "03/07/1989"),
                new Instructor(5, "Kim", "Anderson", "14/09/1992")
        );
        return dtosInstructorToEntities(list);
    }

    public InstructorDTO findInstructorById(int id){
        return findAllInstructor()
                .stream()
                .filter(instructor -> instructor.getId()==id)
                .findFirst()
                .orElseThrow(() -> new BusinessException("Not Found!"));
    }


    /**
     * OVERALL DURATION FUNCION
     * */

    private int overallDuration(Event event){
        int days = 0;
        if(event.getStart().get(Calendar.DATE) < event.getEnd().get(Calendar.DATE)){
            days = (event.getEnd().get(Calendar.DATE) - event.getStart().get(Calendar.DATE));
        }
        return days;
    }








    /**
     * Factory Method ::: EVENT
     * **/

    private Event dtoToEntity(EventDTO eventDTO){
        Event event = new Event();
        BeanUtils.copyProperties(eventDTO, event);
        return event;
    }
    private EventDTO entityToDto(Event event){
        EventDTO eventDTO = new EventDTO();
        eventDTO.setDuration(overallDuration(event) + " days!");
        BeanUtils.copyProperties(event, eventDTO);
        return eventDTO;
    }

    private List<Event> dtosEventToEntities(List<EventDTO> list){
        return list.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }


    /**
     * Factory Method ::: INSTRUCTOR
     * **/
    private InstructorDTO entityToDto(Instructor instructor){
        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setEvents(getInstructorEvents(instructor.getId()));
        BeanUtils.copyProperties(instructor, instructorDTO);
        return instructorDTO;
    }

    private List<InstructorDTO> dtosInstructorToEntities(List<Instructor> list){
        return list.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

}
