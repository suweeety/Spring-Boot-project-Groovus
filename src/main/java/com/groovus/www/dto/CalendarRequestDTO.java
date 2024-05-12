package com.groovus.www.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalendarRequestDTO {

    //tb_calendar
    private String cal_id; // 일정 아이디

    private String cal_title; // 일정 제목

    private String cal_content; // 일정 내용

    private String cal_cate; // 카테고리

    private String cal_startDate; // 시작 날짜

    private String cal_endDate; // 종료 날짜

    private List<String> cal_attach; // 첨부파일

    private List<String> cal_link_list; // 투두리스트

    private List<String> cal_members; // 초대 멤버

    private String pid; // 프로젝트 번호

    private String create_user_id; // 작성자

    private String update_user_id; // 수정

}
