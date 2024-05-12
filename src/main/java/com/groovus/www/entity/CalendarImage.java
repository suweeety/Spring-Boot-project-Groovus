package com.groovus.www.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "calendar")
public class CalendarImage implements Comparable<CalendarImage> {

    @Id
    private String uuid;

    private String fileName;

    private int ord;

    @ManyToOne
    private Calendar calendar;

    @Override
    public int compareTo(CalendarImage other) {
        return this.ord - other.ord;
    }

    public void changeCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
