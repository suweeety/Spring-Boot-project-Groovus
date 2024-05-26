package com.groovus.www.controller;

import com.groovus.www.dto.MessageDTO;
import com.groovus.www.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.PanelUI;
import java.security.PublicKey;

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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/read")
    public ResponseEntity<MessageDTO> readMessage(String lid , String mid){

        MessageDTO messageDTO = messageService.readMessage(Long.parseLong(lid) , Long.parseLong(mid));

        return new ResponseEntity<>(messageDTO,HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/count")
    public ResponseEntity<String> getCount(String pid , String mid){

        String result = messageService.countMessage(Long.parseLong(pid),Long.parseLong(mid)).toString();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete")
    public ResponseEntity<String> deleteMessage(String lid){

        int result = messageService.deleteMessage(Long.parseLong(lid));

        if(result>0){
            return new ResponseEntity<>("success",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("fail",HttpStatus.OK);
        }

    }
}
