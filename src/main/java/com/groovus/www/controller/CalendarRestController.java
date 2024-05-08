package com.groovus.www.controller;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.repository.CalendarRepository;
import com.groovus.www.service.CalendarService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarRestController {

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private CalendarRepository calendarRepository;

    // 특정 일정에 속하는 데이터를 반환
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/read/{pid}/{cal_id}", produces = MediaType.APPLICATION_JSON_VALUE) // 해당 프로젝트의 일정 조회
    public ResponseEntity<CalendarDTO> get(@PathVariable("pid") String pid, @PathVariable("cal_id") String cal_id, Model model, RedirectAttributes rttr) {

        log.info("cal_id****: " + cal_id);
        log.info("pid****: " + pid);
        log.info("---------------------------------------------------------------");
        CalendarDTO dto = calendarService.readOne(Long.parseLong(pid), Long.parseLong(cal_id));
        log.info(dto);
        log.info("---------------------------------------------------------------");
        model.addAttribute("dto", dto);
        model.addAttribute("cal_id", cal_id);
        rttr.addFlashAttribute("cal_id", cal_id);
        rttr.addFlashAttribute("pid", pid);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/register") // 일정 추가
    public String registerPOST(@Valid CalendarDTO calendarDTO, BindingResult bindingResult, RedirectAttributes rttr) {

        log.info("registerPOST 확인");
        log.info("cal_cate 값 확인: " + calendarDTO.getCal_cate());

        if(bindingResult.hasErrors()) {
            log.info("has errors...");
            rttr.addFlashAttribute("errors", bindingResult.getAllErrors());
        }

        // 카테고리를 한글화하는 작업
        if(calendarDTO.getCal_cate().equals("bg-team")) {
            calendarDTO.setCal_cate("팀 회의");
        } else if(calendarDTO.getCal_cate().equals("bg-dept")) {
            calendarDTO.setCal_cate("부서 회의");
        } else if(calendarDTO.getCal_cate().equals("bg-company-event")) {
            calendarDTO.setCal_cate("사내 행사");
        } else if(calendarDTO.getCal_cate().equals("bg-personal-event")) {
            calendarDTO.setCal_cate("개인 일정");
        } else if(calendarDTO.getCal_cate().equals("bg-account-event")) {
            calendarDTO.setCal_cate("거래처 일정");
        } else if(calendarDTO.getCal_cate().equals("bg-business-trip")) {
            calendarDTO.setCal_cate("출장");
        } else {
            calendarDTO.setCal_cate("기타");
        }

        log.info("calendarDTO: " + calendarDTO);

        Long cal_id = calendarService.register(calendarDTO);

        rttr.addFlashAttribute("result", cal_id);

        return "redirect:/calendar/schedule";

    }


//    @GetMapping("/readTest")
//    public String readAndModify(@Valid CalendarDTO calendarDTO, Model model) throws Exception{
//
//        log.info("readAndModify 메서드 확인");
//
//        //calendarService.modify(calendarDTO);
//
//        //log.info("readAndModify 메서드 calendarDTO 확인: " + calendarDTO);
//
//        //model.addAttribute("calendarDTO", calendarDTO);
//
//        return "redirect:/calendar/schedule";
//    }

//    @GetMapping("/readTest")
//    public String test(Long cal_id, Model model) throws Exception {
//
//        log.info("test 메서드 확인");
//
//        CalendarDTO calendarDTO = calendarService.readOne(cal_id);
//
//        log.info("cal_id@@!!!: " + cal_id);
//        log.info("calendarDTO@@!!!: " + calendarDTO);
//
//        model.addAttribute("calendarDTO", calendarDTO);
//
//        return "redirect:/calendar/schedule";
//    }

    @PutMapping("/{cal_id}") // 일정 수정
    public ResponseEntity<String> modify(@RequestBody CalendarDTO calendarDTO) {

        log.info(calendarDTO);

//        calendarService.modify(calendarDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/{cal_id}") // 일정 삭제
    public ResponseEntity<String> remove(@PathVariable("cal_id") Long cal_id) {

        log.info("cal_id in remove method: " + cal_id);

        calendarService.remove(cal_id);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
