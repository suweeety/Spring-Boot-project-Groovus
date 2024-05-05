package com.groovus.www.controller;

import com.groovus.www.dto.MessageDTO;
import com.groovus.www.dto.ProjectPageRequestDTO;
import com.groovus.www.dto.ProjectPageResponseDTO;
import com.groovus.www.dto.TaskDTO;
import com.groovus.www.service.MessageService;
import jdk.jfr.Label;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/message")
public class MessageController {

   private final MessageService messageService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list/{pid}/{mid}/{projectName}")
    public String taskList(@PathVariable("pid") String pid , @PathVariable("projectName") String projectName,@PathVariable("mid") String mid, ProjectPageRequestDTO pageRequestDTO, Model model){

        ProjectPageResponseDTO<MessageDTO> responseDTO = messageService.getMessageListWithPaging(pageRequestDTO,Long.parseLong(pid),Long.parseLong(mid));

        model.addAttribute("responseDTO",responseDTO);
        model.addAttribute("pageRequestDTO",pageRequestDTO);
        model.addAttribute("pid",pid);
        model.addAttribute("projectName",projectName);

        log.info("==============================");
        log.info(pid);
        log.info("==============================");

        return "/message/messageList";

    }

}
