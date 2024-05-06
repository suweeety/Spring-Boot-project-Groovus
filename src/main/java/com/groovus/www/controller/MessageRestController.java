package com.groovus.www.controller;

import com.groovus.www.dto.MessageDTO;
import com.groovus.www.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageRestController {

    private final MessageService messageService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(MessageDTO messageDTO, String pid){
        //쪽지 보내기

        messageService.sendMessage(messageDTO,Long.parseLong(pid));
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
