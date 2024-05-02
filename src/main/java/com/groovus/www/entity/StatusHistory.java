package com.groovus.www.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "tid")
public class StatusHistory {

    //업무 상태 변경이력 엔터티
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hid; // 히스토리아이디

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Task tid;

    private String status; //현재상태

    private String prevStatus; //이전상태

    private String uid; //상태변경자

    private String modDate; //상태 변경일
}
