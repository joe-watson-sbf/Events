package com.mintsoftware.events.dto;

import lombok.Data;
import java.util.Calendar;


@Data
public class EventDTO {
    private int id;
    private Calendar start;
    private Calendar end;
    private String type;
    private String description;
    private String duration;
    private boolean enable;
}
