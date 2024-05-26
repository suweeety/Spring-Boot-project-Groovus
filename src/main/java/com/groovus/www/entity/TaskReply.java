package com.groovus.www.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "task")
public class TaskReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid; //댓글번호

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Task task; //업무번호

    private String uid; // 작성자 아이디

    @Column(length = 2000)
    private String replyContent; //댓글 내용

}
