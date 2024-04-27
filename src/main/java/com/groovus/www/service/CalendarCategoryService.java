package com.groovus.www.service;

import com.groovus.www.dto.CalendarCategoryDTO;
import com.groovus.www.dto.CalendarDTO;

public interface CalendarCategoryService {

    String register(CalendarCategoryDTO calendarCategoryDTO);

    CalendarCategoryDTO readOne(Long calCate_id);

    void modify(CalendarCategoryDTO calendarCategoryDTO);

    void remove(Long calCate_id);
}
