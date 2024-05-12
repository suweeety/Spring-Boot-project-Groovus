package com.groovus.www.controller;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.service.CalendarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/calendar")
@Log4j2
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;


}
