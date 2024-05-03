package com.groovus.www.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskReReplyDTO {

    private String rrid; //대댓글번호


    private String rid; //댓글번호
    private String uid; //작성자

    private String replyText;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
