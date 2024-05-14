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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
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

    @PostMapping("/project/info")
    public ResponseEntity<RegisterProjectDTO> getProjectInfo(String pid){
        //pid로 프로젝트 정보를 반환함

        RegisterProjectDTO projectDTO = projectService.getProjectDTO(Long.parseLong(pid));

        return new ResponseEntity<>(projectDTO,HttpStatus.OK);
    }

    @PostMapping("/project/changeDes")
    public ResponseEntity<String> changeDescription(String pid, String des){

       boolean result = projectService.changeDes(Long.parseLong(pid),des);

        if(result){
            return new ResponseEntity<>("y",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("n",HttpStatus.OK);
        }
    }

    @PostMapping("/project/changePw")
    public ResponseEntity<String> changePassword(String pid, String newPw){

        boolean result = projectService.changePw(Long.parseLong(pid),newPw);

        if(result){
            return new ResponseEntity<>("y",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("n",HttpStatus.OK);
        }
    }

    @PostMapping("/project/deleteMember")
    public ResponseEntity<String> deleteMember(String pid,String deleteMember){

        boolean result = projectService.deleteMember(Long.parseLong(pid),deleteMember);

        if(result){
            return new ResponseEntity<>("y",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("n",HttpStatus.OK);
        }
    }

    @PostMapping("/project/addMember")
    public ResponseEntity<String> addMember(String pid, String[] invitedMemberList){

        boolean result = projectService.addMember(Long.parseLong(pid), Arrays.asList(invitedMemberList));

        if(result){
            return new ResponseEntity<>("y",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("n",HttpStatus.OK);
        }

    }

}
