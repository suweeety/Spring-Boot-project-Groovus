package com.groovus.www.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name="tb_calendar_attach")
@ToString
public class CalendarAttach extends BaseEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long calAtt_id;

    @ManyToOne(fetch = FetchType.LAZY) // 하위 클래스로서의 관점: 여러 첨부파일은 하나의 일정을 가짐
    @JoinColumn(name = "cal_id")
    private Calendar cal_id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String uuid;
    @Column(nullable = false)
    private String uploadPath;

}