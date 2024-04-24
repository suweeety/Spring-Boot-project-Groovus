package com.groovus.www.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class CalendarCategory {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long cal_category;
}
