package com.mintsoftware.events.model;

import lombok.Data;


@Data
public class Instructor {

    private int id;
    private String firstName;
    private String lastName;
    private String birthday;

    public Instructor(int id, String firstName, String lastName, String birthday) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

}
