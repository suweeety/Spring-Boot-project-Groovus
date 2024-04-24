package com.groovus.www.controller;

import com.groovus.www.dto.RegisterProjectDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/project")
public class ProjectController {

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public String registerProject(RegisterProjectDTO pDTO){

        log.info("================등록 정보====================");
        log.info(pDTO);
        log.info("============================================");


        return "/main/main";
    }





}
