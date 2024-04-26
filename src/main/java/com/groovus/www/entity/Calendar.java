package com.groovus.www.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name="tb_calendar")
@ToString
public class Calendar extends BaseEntity{
    /*
    Calendar Entity 여러 개가 하나의 Project Entity를 가짐
    Calendar Entity 하나는 여러 명의 User Entity를 가짐
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cal_id; // 캘린더 아이디

    @Column(length = 200, nullable = false)
    private String cal_title; // 일정 제목

    @Column(length = 5000, nullable = false)
    private String cal_content; // 일정 내용

    @OneToOne
    private CalendarCategory cal_category;

    @Column(nullable = true)
    private String cal_startDate;

    @Column(nullable = true)
    private String cal_endDate;

    public void change(String cal_title, String cal_content, CalendarCategory cal_category, String cal_startDate, String cal_endDate) {

        this.cal_title = cal_title;
        this.cal_content = cal_content;
        this.cal_category = cal_category;
        this.cal_startDate = cal_startDate;
        this.cal_endDate = cal_endDate;
    }

    // 수정일 및 등록일은 BaseEntity 참고
}
