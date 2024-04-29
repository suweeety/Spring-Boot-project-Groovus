package com.groovus.www.controller;

import com.groovus.www.dto.*;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Project;
import com.groovus.www.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
public class ProjectRestController {

    private final ProjectService projectService;

    @PostMapping("/project/myproject")
    public ResponseEntity<List<RegisterProjectDTO>> getMyProjectList(String mid){

        List<RegisterProjectDTO> projectList = projectService.getProjectList(Long.parseLong(mid));

        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    @PostMapping("/project/memberList")
    public ResponseEntity<List<MemberInfoDTO>> getProjectMembers(String pid){


        log.info("================프로젝트 레스트 컨트롤러=====================");

        log.info(pid);
        log.info("====================================================");
        List<MemberInfoDTO> memberList = projectService.getProjectMembers(pid);
        log.info(memberList);
        log.info("====================================================");
        return new ResponseEntity<>(memberList,HttpStatus.OK);
    }

}
