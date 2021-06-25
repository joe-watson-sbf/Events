package com.mintsoftware.events.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Calendar;


@Entity(name = "events")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private Calendar start;
    @Column(nullable = false)
    private Calendar end;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String description;
    private boolean enable;


}
