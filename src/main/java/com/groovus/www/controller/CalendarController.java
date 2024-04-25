package com.groovus.www.controller;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.service.CalendarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {

    @Autowired
    private CalendarService calendarService;


    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public String registerPOST(@Valid CalendarDTO calendarDTO, BindingResult bindingResult, RedirectAttributes rttr) {

        log.info("registerPOST 확인");

        if(bindingResult.hasErrors()) {
            log.info("has errors...");
            rttr.addFlashAttribute("errors", bindingResult.getAllErrors());
        }

        log.info("calendarDTO: " + calendarDTO);

        Long cal_id = calendarService.register(calendarDTO);

        rttr.addFlashAttribute("result", cal_id);

        return "redirect:/calendar/schedule";

    }


}
