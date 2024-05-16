package com.groovus.www.controller;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.dto.CalendarRequestDTO;
import com.groovus.www.entity.Calendar;
import com.groovus.www.entity.Member;
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
public class CalendarRestController { // JSON을 주로 보내는 목적으로 사용

    @Autowired
    private CalendarService calendarService;

    // 프로젝트와 일정에 해당하는 값 반환
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/read/{pid}/{cal_id}") // {pid},{cal_id}를 쿼리스트링으로
    public ResponseEntity<CalendarDTO> get(@PathVariable("pid") String pid, @PathVariable("cal_id") String cal_id) {

        log.info("cal_id****: " + cal_id);
        log.info("pid****: " + pid);
        log.info("-------------------------readController--------------------------------------");
        // pid와 cal_id를 이용하여 값을 조회해 옴
        CalendarDTO dto = calendarService.readOne(Long.parseLong(pid), Long.parseLong(cal_id));
        log.info(dto.getCal_members());
        log.info("-------------------------readController dto--------------------------------------");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/register") // 일정 추가
    public ResponseEntity<String> registerPOST(CalendarRequestDTO calendarRequestDTO) {

        log.info("==========================================================");
        log.info("registerPOST 확인");
        log.info(calendarRequestDTO.getPid());
        log.info(calendarRequestDTO.getCal_cate());
        log.info(calendarRequestDTO.getCal_members().stream().toArray());
        log.info(calendarRequestDTO.getCreate_user_id());
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
        calendarService.register(calendarRequestDTO , Long.parseLong(calendarRequestDTO.getPid())); // (pid는 브라우저 쪽에서 받아옴)

        return new ResponseEntity<>("success",HttpStatus.OK);

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify") // 일정 수정
    public ResponseEntity<String> modify(String update_user_id, CalendarRequestDTO calendarRequestDTO) {

        log.info("==================++++++++++===================");
        log.info("modify:"+calendarRequestDTO);
        log.info("==================++++++++++===================");

        calendarService.modify(calendarRequestDTO, Long.parseLong(calendarRequestDTO.getPid()), Long.parseLong(calendarRequestDTO.getCal_id()));

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{pid}/{cal_id}") // 일정 삭제
    public ResponseEntity<String> remove(@PathVariable("pid") Long pid, @PathVariable("cal_id") Long cal_id) {

        log.info("cal_id in remove method: " + cal_id);

        calendarService.remove(cal_id);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
