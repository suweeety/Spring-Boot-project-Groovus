package com.groovus.www.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"sender","receiver"})
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lid; // 쪽지번호 (letterId ... )

    private String title; //쪽지 제목
    
    @Column(length = 1000)
    private String content; // 쪽지 내용

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project; // 프로젝트

    @ManyToOne(fetch = FetchType.LAZY)
    private Member receiver; //받는이

    @ManyToOne(fetch = FetchType.LAZY)
    private Member sender; //보낸이

    private LocalDateTime sendDate; //보낸날짜

    @Builder.Default
    private boolean del = false; //삭제 여부

    public void changeDel(boolean del){
        //메세지 삭제 여부 변경
        this.del = del;
    }

}
