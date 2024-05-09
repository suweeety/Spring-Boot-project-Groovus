package com.groovus.www.controller;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.dto.CalendarRequestDTO;
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
    public ResponseEntity<CalendarDTO> get(@PathVariable("pid") String pid, @PathVariable("cal_id") String cal_id) {

        log.info("cal_id****: " + cal_id);
        log.info("pid****: " + pid);
        log.info("---------------------------------------------------------------");
        CalendarDTO dto = calendarService.readOne(Long.parseLong(pid), Long.parseLong(cal_id));
        log.info(dto);
        log.info("---------------------------------------------------------------");

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/register") // 일정 추가
    public ResponseEntity<String> registerPOST(CalendarRequestDTO calendarRequestDTO) {


        log.info("==========================================================");
        log.info("registerPOST 확인");
        log.info(calendarRequestDTO.getPid());
        log.info(calendarRequestDTO.getCal_cate());
        log.info("==========================================================");

// 카테고리를 한글화하는 작업
        if(calendarRequestDTO.getCal_cate().equals("bg-team")) {
            calendarRequestDTO.setCal_cate("팀 회의");
        } else if(calendarRequestDTO.getCal_cate().equals("bg-dept")) {
            calendarRequestDTO.setCal_cate("부서 회의");
        } else if(calendarRequestDTO.getCal_cate().equals("bg-company-event")) {
            calendarRequestDTO.setCal_cate("사내 행사");
        } else if(calendarRequestDTO.getCal_cate().equals("bg-personal-event")) {
            calendarRequestDTO.setCal_cate("개인 일정");
        } else if(calendarRequestDTO.getCal_cate().equals("bg-account-event")) {
            calendarRequestDTO.setCal_cate("거래처 일정");
        } else if(calendarRequestDTO.getCal_cate().equals("bg-business-trip")) {
            calendarRequestDTO.setCal_cate("출장");
        } else {
            calendarRequestDTO.setCal_cate("기타");
        }

        log.info("calendarRequestDTO: " + calendarRequestDTO);

        // calendarService의 register메서드 호출하여 Long cal_id에 대입(calendarDTO 값 가지고 있음)
        Long cal_id = calendarService.register(calendarRequestDTO , Long.parseLong(calendarRequestDTO.getPid()));






        return new ResponseEntity<>("success",HttpStatus.OK);

    }

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
