package com.groovus.www.controller;

import com.groovus.www.dto.TaskDTO;
import com.groovus.www.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskService taskService;

    @PostMapping("/task/countall")
    public ResponseEntity<Long> getTaskCount(String pid){

        Long count = taskService.getTaskCount(Long.parseLong(pid));
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PostMapping ("/task/view")
    public ResponseEntity<TaskDTO> readTask(String tid){

        log.info("=============================");
        log.info(tid);
        log.info("=============================");

        TaskDTO taskDTO = taskService.readTask(tid);

        return new ResponseEntity<>(taskDTO,HttpStatus.OK);
    }

}
