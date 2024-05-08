package com.groovus.www.service;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.dto.MemberDTO;
import com.groovus.www.entity.Calendar;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Project;
import lombok.RequiredArgsConstructor;

import java.util.List;


public interface CalendarService {



    Long register(CalendarDTO calendarDTO); // 일정 등록

    List<CalendarDTO> getList(Long pid); // 특정 프로젝트의 일정

    CalendarDTO readOne(Long pid, Long cal_id);

    void modify(CalendarDTO calendarDTO, Member createMember); // 일정 수정

    void remove(Long cal_id); // 일정 삭제


    default Calendar dtoToEntity(CalendarDTO dto, Member createMember) {

        Project project = Project.builder().pid(dto.getPid()).build();

        Calendar calendar = Calendar.builder()
                .cal_id(dto.getCal_id())
                .cal_title(dto.getCal_title())
                .cal_content(dto.getCal_content())
                .cal_cate(dto.getCal_cate())
                .cal_startDate(dto.getCal_startDate())
                .cal_endDate(dto.getCal_endDate())
                .cal_attach(dto.getCal_attach())
                .cal_link(dto.getCal_link())
                .cal_member(dto.getCal_member())
                .create_user_id(createMember)
                .project(project)
                .build();

        return calendar;
    }

    default CalendarDTO entityToDto(Calendar calendar) {
        CalendarDTO dto = CalendarDTO.builder()
                .cal_id(calendar.getCal_id())
                .cal_title(calendar.getCal_title())
                .cal_content(calendar.getCal_content())
                .cal_cate(calendar.getCal_cate())
                .cal_startDate(calendar.getCal_startDate())
                .cal_endDate(calendar.getCal_endDate())
                .cal_attach(calendar.getCal_attach())
                .cal_link(calendar.getCal_link())
                .cal_member(calendar.getCal_member())
                .create_user_id(calendar.getCreate_user_id().toString())
                .pid(calendar.getProject().getPid())
                .build();
        return dto;
    }


}
