package com.groovus.www.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name="tb_calendar_link")
public class CalendarLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cal_link_id;

    @Column(length = 200, nullable = false)
    private String cal_link_title; // 일정 제목

    @Column(length = 5000, nullable = false)
    private String cal_link; // 일정 내용
}
