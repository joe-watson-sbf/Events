package com.mintsoftware.events.dto;

import lombok.Data;
import java.util.List;

@Data
public class InstructorDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String birthday;
    private List<EventDTO> events;
}
