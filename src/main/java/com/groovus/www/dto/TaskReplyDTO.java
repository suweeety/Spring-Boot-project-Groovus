package com.groovus.www.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskReplyDTO {

    private String rid; //댓글번호 (자동생성)

    private String tid; // 업무 번호

    private String uid; //댓글 작성자

    private String replyContent; //댓글 내용
    
    private LocalDateTime regDate; //등록일
    private LocalDateTime modDate; //수정일


}
