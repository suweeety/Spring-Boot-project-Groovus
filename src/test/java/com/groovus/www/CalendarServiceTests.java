//package com.groovus.www;
//
//import com.groovus.www.dto.CalendarDTO;
//import com.groovus.www.service.CalendarService;
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//@Log4j2
//public class CalendarServiceTests {
//
//    @Autowired
//    private CalendarService calendarService;
//
//    @Test
//    public void testRegister() {
//        log.info(calendarService.getClass().getName());
//
//        CalendarDTO calendarDTO = CalendarDTO.builder()
//                .cal_title("Sample title")
//                .cal_content("Sample content")
//                .build();
//        Long cal_id = calendarService.register(calendarDTO);
//
//        log.info("cal_id: "+ cal_id);
//    }
//
//    @Test
//    public void testModify() {
//        CalendarDTO calendarDTO = CalendarDTO.builder()
//                .cal_id(7L)
//                .cal_title("Updated")
//                .cal_content("Updated")
//                .build();
//        calendarService.modify(calendarDTO);
//    }
//
//    @Test
//    public void testRemove() {
//        CalendarDTO calendarDTO = CalendarDTO.builder()
//                .cal_id(2L) // 삭제할 일정의 ID
//                .build();
//
//        // 삭제할 일정의 ID를 사용하여 일정을 삭제하는 서비스 메서드 호출
//        calendarService.remove(calendarDTO.getCal_id());
//    }
//}
