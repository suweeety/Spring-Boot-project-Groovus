package com.groovus.www.service;

import com.groovus.www.dto.CalendarCategoryDTO;
import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.entity.Calendar;

public interface CalendarService {

    Long register(CalendarDTO calendarDTO);

    CalendarDTO readOne(Long cal_id);

    void modify(CalendarDTO calendarDTO, CalendarCategoryDTO calendarCategoryDTO);

    void remove(Long cal_id);
}
