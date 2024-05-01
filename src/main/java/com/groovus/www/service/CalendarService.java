package com.groovus.www.service;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.entity.Calendar;

public interface CalendarService {

    Long register(CalendarDTO calendarDTO);

    CalendarDTO readOne(Long cal_id);

    void modify(CalendarDTO calendarDTO);

    void remove(Long cal_id);


    default Calendar dtoToEntity(CalendarDTO dto) {

        Calendar calendar = Calendar.builder()
                .cal_id(dto.getCal_id())
                .cal_title(dto.getCal_title())
                .cal_content(dto.getCal_content())
                .cal_cate(dto.getCal_cate())
                .cal_startDate(dto.getCal_startDate())
                .cal_endDate(dto.getCal_endDate())
                .cal_attach(dto.getCal_attach())
                .cal_link(dto.getCal_link())
                .build();

        return calendar;
    }


}
