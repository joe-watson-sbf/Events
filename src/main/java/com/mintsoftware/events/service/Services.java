package com.mintsoftware.events.service;

import com.mintsoftware.events.dao.AppDAO;
import com.mintsoftware.events.dto.EventDTO;
import com.mintsoftware.events.dto.InstructorDTO;
import com.mintsoftware.events.dto.ResponseDto;
import com.mintsoftware.events.model.Event;
import com.mintsoftware.events.model.businessException.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.List;

@Service
public class Services {
    private final String NULL_EVENT = "All field is requied!";
    private final String TYPE_EVENT_REQUIRED = "The event type's is empty!";
    private final String DESCRIPTION_EVENT_REQUIRED = "The event description's is empty!";
    private final String ERROR_DATE = "The start date is higher than the end!";
    private final String EVENT_CONFLICT = "There's is a conflict in your event list!";
    private final String EVENT_CREATED = "Event Created!";
    private final String EVENT_UPDATED = "Event updated!";
    private final String EVENT_DELETED = "Event deleted!";
    private final String EVENT_NOT_FOUND = "Event not found!";
    private final String EVENT_ASSIGNED = "Event assigned!";
    private final String EVENT_NOT_ENABLE = "You can't assigns this event, it's not enable to assign!";

    @Autowired
    private AppDAO dao;

    /**
     * EVENTS CRUD
     * **/
    public ResponseDto saveEvent(EventDTO eventDTO){
        validateEventDTO(eventDTO);
        dao.create(eventDTO);
        return new ResponseDto(EVENT_CREATED);
    }

    public ResponseDto saveEvents(List<EventDTO> eventDTOS){
        eventDTOS.forEach(this::validateEventDTO);
        dao.saveEventList(eventDTOS);
        return new ResponseDto(EVENT_CREATED);
    }

    public ResponseDto updateEvent(EventDTO eventDTO){
        if (dao.exists(eventDTO.getId())){
            validateEventDTO(eventDTO);
            dao.create(eventDTO);
            return new ResponseDto(EVENT_UPDATED);
        }else throw new BusinessException(EVENT_NOT_FOUND);
    }

    public ResponseDto deleteEvent(EventDTO eventDTO){
        if (dao.exists(eventDTO.getId())){
            dao.delete(eventDTO);
            return new ResponseDto(EVENT_DELETED);
        }else throw new BusinessException(EVENT_NOT_FOUND);
    }

    public EventDTO getOneEvents(int id){
        if (dao.exists(id)){
            return dao.getEventById(id);
        }else throw new BusinessException(EVENT_NOT_FOUND);
    }

    public List<EventDTO> getAllEvents(){
        return dao.findAllEvent();
    }


    /**
     * ACCESS TO SAMPLE DATA INSTRUCTOR
     * **/

    public List<InstructorDTO> getAllInstructors(){
        return dao.findAllInstructor();
    }

    public InstructorDTO getInstructorById(int id){
        return dao.findInstructorById(id);
    }

    public ResponseDto instructorEvent(int idInstructor, int idEvent){
        if(dao.saveInstructorEvents(idInstructor,idEvent)){
            return new ResponseDto(EVENT_ASSIGNED);
        }else {
            throw new BusinessException(EVENT_NOT_ENABLE);
        }
    }

    public void deleteInstructorEvent( int idEvent){
        dao.deleteInstructorEvents(idEvent);
    }


    /**
     * My All Verification DTOs Method
     * **/

    private void validateEventDTO(EventDTO eventDTO){
        eventDTO.setEnable(true);
        try{
            if(eventDTO.getType().isBlank()){
                throw new BusinessException(TYPE_EVENT_REQUIRED);
            }
            if(eventDTO.getDescription().isBlank()){
                throw new BusinessException(DESCRIPTION_EVENT_REQUIRED);
            }
        }catch (NullPointerException ex){
            throw new BusinessException(NULL_EVENT);
        }
        dateValidation(eventDTO);
    }

    private void dateValidation(EventDTO eventDTO){
        Calendar start = eventDTO.getStart();
        Calendar end = eventDTO.getEnd();

        if(start.get(Calendar.YEAR) > end.get(Calendar.YEAR)){
            throw new BusinessException(ERROR_DATE);
        }
        if(start.get(Calendar.YEAR) == end.get(Calendar.YEAR)){
            if(start.get(Calendar.MONTH) > end.get(Calendar.MONTH)){
                throw new BusinessException(ERROR_DATE);
            }

            if(start.get(Calendar.MONTH) == end.get(Calendar.MONTH)){
                if(start.get(Calendar.HOUR_OF_DAY) > end.get(Calendar.HOUR_OF_DAY)){
                    throw new BusinessException(ERROR_DATE);
                }

                if(start.get(Calendar.DATE) == end.get(Calendar.DATE)){
                    throw new BusinessException(EVENT_CONFLICT);
                }
            }
        }
    }

}
