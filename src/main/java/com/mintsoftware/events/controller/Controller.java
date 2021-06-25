package com.mintsoftware.events.controller;

import com.mintsoftware.events.dto.EventDTO;
import com.mintsoftware.events.dto.InstructorDTO;
import com.mintsoftware.events.dto.ResponseDto;
import com.mintsoftware.events.model.businessException.BusinessException;
import com.mintsoftware.events.service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class Controller {

    @Autowired
    private Services service;


    @GetMapping("/instructor/{id}")
    public ResponseEntity<Object> saveInstructor(@PathVariable int id){
        try {
            return new ResponseEntity<>(service.getInstructorById(id), HttpStatus.OK);
        }catch (BusinessException ex){
            return new ResponseEntity<>(new ResponseDto(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/instructor")
    public ResponseEntity<List<InstructorDTO>> getInstructor(){
        return new ResponseEntity<>(service.getAllInstructors(), HttpStatus.OK);
    }

    @PostMapping("/instructor/{idInstructor}/{idEvent}")
    public ResponseEntity<ResponseDto> assignEventToInstructor(@PathVariable("idInstructor") int idInstructor, @PathVariable("idEvent") int idEvent){
        try {
            return new ResponseEntity<>(service.instructorEvent(idInstructor,idEvent), HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.IM_USED);
        }
    }

    @DeleteMapping("/instructor/{idEvent}")
    public void unassignEventToInstructor( @PathVariable int idEvent){
        service.deleteInstructorEvent(idEvent);
    }

    @GetMapping("/event")
    public ResponseEntity<List<EventDTO>> getEvents(){
        return new ResponseEntity<>(service.getAllEvents(), HttpStatus.OK);
    }

    @PostMapping("/event")
    public ResponseEntity<ResponseDto> saveEvent(@RequestBody EventDTO eventDTO){
        try{
            return new ResponseEntity<>(service.saveEvent(eventDTO), HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/events")
    public ResponseEntity<ResponseDto> saveEvent(@RequestBody List<EventDTO> eventDTOs){
        try{
            return new ResponseEntity<>(service.saveEvents(eventDTOs), HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/event")
    public ResponseEntity<ResponseDto> updateEvent(@RequestBody EventDTO eventDTO){
        try{
            return new ResponseEntity<>(service.updateEvent(eventDTO), HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<Object> getEventById(@PathVariable Integer id){
        try{
            return new ResponseEntity<>(service.getOneEvents(id), HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/event")
    public ResponseEntity<ResponseDto> deleteEvent(@RequestBody EventDTO eventDTO){
        try{
            return new ResponseEntity<>(service.deleteEvent(eventDTO), HttpStatus.OK);
        }catch (BusinessException ex){
            return new ResponseEntity<>(new ResponseDto(ex.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

}
