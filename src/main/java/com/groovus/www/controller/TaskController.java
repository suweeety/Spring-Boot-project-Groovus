package com.groovus.www.controller;

import com.groovus.www.dto.TaskDTO;
import com.groovus.www.service.TaskService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/task")
@RequiredArgsConstructor
@Log4j2
public class TaskController {

    private final TaskService taskService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    public String registerTask(TaskDTO dto , String pid , String projectName , RedirectAttributes redirectAttributes){

        log.info("============================================");
        log.info(dto);
        log.info(projectName);
        log.info("============================================");

        Long tid =taskService.registerTask(dto);

        if(tid>0){

            try {
                projectName = URLEncoder.encode(projectName, "UTF-8");

            } catch (UnsupportedEncodingException e) {

                throw new RuntimeException(e);
            }

            String redirectUrl = "/project/task/" + pid + "/" + projectName;

            return "redirect:" + redirectUrl;
        }else{

            redirectAttributes.addFlashAttribute("erroeMsg","업무 등록에 실패했습니다");
            String redirectUrl = "/project/task/" + pid + "/" + projectName;

            return "redirect:" + redirectUrl;
        }
    }




}
