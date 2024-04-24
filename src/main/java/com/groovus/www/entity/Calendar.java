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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long cal_id; // 캘린더 아이디

    @ManyToOne(fetch = FetchType.LAZY) // 하위 클래스로서의 관점: 여러 일정은 하나의 프로젝트를 가짐
    @JoinColumn(name = "pId")
    private Project pId; // 프로젝트 아이디

    @ManyToMany // 상위 클래스로서의 관점: 여러 일정은 여러 유저를 가짐
    @Builder.Default
    @Column(nullable = true)
    private List<User> uIds = new ArrayList<>(); // 일정에 초대되는 멤버들

    @OneToMany // 상위 클래스로서의 관점: 하나의 일정은 여러 첨부파일을 가짐
    @JoinColumn(name = "calAtt_id")
    @Column(nullable = true)
    private Set<CalendarAttach> cal_attach; // 첨부파일

    @OneToMany
    @JoinColumn(name = "cal_category")
    @Builder.Default
    @Column(nullable = false)
    private List<CalendarCategory> calendarCategories = new ArrayList<>();

    @Column(length = 200, nullable = false)
    private String cal_title; // 일정 제목

    @Column(length = 5000, nullable = false)
    private String cal_content; // 일정 내용

    @Column(nullable = false)
    private String cal_color; // 일정 색

    @Column(nullable = false)
    private String cal_category; // 일정 카테고리

    @Column(nullable = true)
    private String cal_period; // 일정 기간

    @Column(nullable = true)
    private String cal_dday; // 디데이

    @Column(length = 5000, nullable = true)
    private String cal_link; // 링크

    @Column(nullable = true)
    private String cal_todo; // 투두리스트

    @ManyToOne(fetch = FetchType.LAZY) // 하위 클래스로서의 관점: 여러 일정은 하나의 등록인을 가짐
    @JoinColumn(name = "uId")
    private User create_user_id; // 등록인

    @ManyToMany
    @Builder.Default
    @Column(nullable = true)
    private List<User> update_user_id = new ArrayList<>(); // 수정인

    public void change(String cal_title, String cal_content, String cal_color, String cal_category, String cal_period,
                       String cal_todo, String cal_dday, Set<CalendarAttach> cal_attach, String cal_link) {

        this.cal_title = cal_title;
        this.cal_content = cal_content;
        this.cal_color = cal_color;
        this.cal_category = cal_category;
        this.cal_period = cal_period;
        this.cal_todo = cal_todo;
        this.cal_dday = cal_dday;
        this.cal_attach = cal_attach;
        this.cal_link = cal_link;
    }

    // 수정일 및 등록일은 BaseEntity 참고
}
