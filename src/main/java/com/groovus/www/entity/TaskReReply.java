package com.groovus.www.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "taskReply")
public class TaskReReply extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rrid; //대댓글번호

    @ManyToOne(fetch = FetchType.LAZY)
    private TaskReply taskReply; //댓글번호

    private String uid; //작성자

    @Column(length = 2000)
    private String replyText; //대댓글내용

}
