package com.groovus.www.controller;

import com.groovus.www.dto.ProjectPageRequestDTO;
import com.groovus.www.dto.ProjectPageResponseDTO;
import com.groovus.www.dto.RegisterProjectDTO;
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



}
