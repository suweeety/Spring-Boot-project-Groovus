package com.groovus.www.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="tb_calendar")
@ToString(exclude = "imageSet")
@Log4j2
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

    @Column(nullable = false)
    private String cal_cate; // 일정 카테고리

    @Column(nullable = false)
    private String cal_startDate; // 일정 시작날짜

    @Column(nullable = false)
    private String cal_endDate; // 일정 종료날짜

    @Column(nullable = true)
    private String cal_link_list; // 링크

    @ManyToMany
    @Column(nullable = true)
    private List<CalendarAttach> cal_attach; // 첨부파일

    @ManyToMany(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @Builder.Default
    @Column(nullable = true)
    private Set<Member> cal_members = new HashSet<>(); // 초대 멤버

    @OneToMany(mappedBy = "calendar",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY) //CalendarImage의 calendar 변수
    @Builder.Default
    private Set<CalendarImage> imageSet = new HashSet<>(); // 이미지 첨부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    private Project project; // 프로젝트 아이디

    @JoinColumn(name = "createmid", updatable=false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member create_user_id; // 작성자

    @JoinColumn(name = "updatemid")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Member update_user_id; // 최근 작성자

    /*----------------------------------------------------------------*/

    public void addMember(Member member) {
        this.cal_members.add(member);
        log.info("=======멤버 추가됨========");
        log.info(member);
    }

    public void addImage(String uuid, String fileName) {
        CalendarImage calendarImage = CalendarImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .calendar(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(calendarImage);
    }

    public void setProject(Project project){
        this.project = project;
    }
    public void setUpdate_user_id(Member update_user_id) {
        this.update_user_id = update_user_id;
    }

    public void clearImages() {
        imageSet.forEach(calendarImage -> calendarImage.changeCalendar(null));
        this.imageSet.clear();
    }

    // modify용 메서드
    public void change(String cal_title, String cal_content, String cal_cate, String cal_startDate, String cal_endDate, String cal_link_list) {

        this.cal_title = cal_title;
        this.cal_content = cal_content;
        this.cal_cate = cal_cate;
        this.cal_startDate = cal_startDate;
        this.cal_endDate = cal_endDate;
        this.cal_link_list = cal_link_list;

    }

    // 수정일 및 등록일은 BaseEntity 참고
}
