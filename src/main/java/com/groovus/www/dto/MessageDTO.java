package com.groovus.www.dto;

import com.groovus.www.entity.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Data
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {

    private String lid ; //쪽지 번호

    private  String title; //쪽지 제목
    private  String content; //쪽지 내용

    private  String receiver; //받는이

    private  String sender; //보낸이
    
    private MessageStatus messageStatus; //읽음 여부

    private LocalDateTime sendDate; //보낸날짜
    
}
