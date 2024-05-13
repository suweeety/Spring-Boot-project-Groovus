package com.groovus.www.service;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.dto.CalendarRequestDTO;
import com.groovus.www.dto.MemberDTO;
import com.groovus.www.entity.Calendar;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Project;
import lombok.RequiredArgsConstructor;

import java.util.List;


public interface CalendarService {

    Long register(CalendarRequestDTO calendarRequestDTO , Long pid); // 일정 등록

    List<CalendarDTO> getList(Long pid); // 특정 프로젝트의 일정 목록

    CalendarDTO readOne(Long pid, Long cal_id); // 일정 가져오기

    void modify(CalendarRequestDTO calendarRequestDTO, Long pid, Long cal_id); // 일정 수정

    boolean remove(Long cal_id); // 일정 삭제

    int countSchedule(Long pid); // 일정 수 세기

    default CalendarDTO entityToDto(Calendar calendar) {

        CalendarDTO dto = CalendarDTO.builder()
                .cal_id(calendar.getCal_id())
                .cal_title(calendar.getCal_title())
                .cal_content(calendar.getCal_content())
                .cal_cate(calendar.getCal_cate())
                .cal_startDate(calendar.getCal_startDate())
                .cal_endDate(calendar.getCal_endDate())
                .cal_attach(calendar.getCal_attach())
                .cal_link_list(calendar.getCal_link_list())
//                .cal_members(calendar.getCal_members())
                .pid(calendar.getProject().getPid())
                .build();
        return dto;
    }


}
