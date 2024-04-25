package com.groovus.www;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.service.CalendarService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class CalendarServiceTests {

    @Autowired
    private CalendarService calendarService;

    @Test
    public void testRegister() {
        log.info(calendarService.getClass().getName());

        CalendarDTO calendarDTO = CalendarDTO.builder()
                .cal_title("Sample title")
                .cal_content("Sample content")
                .build();
        Long cal_id = calendarService.register(calendarDTO);

        log.info("cal_id: "+ cal_id);
    }

    @Test
    public void testModify() {
        CalendarDTO calendarDTO = CalendarDTO.builder()
                .cal_id(7L)
                .cal_title("Updated")
                .cal_content("Updated")
                .build();
        calendarService.modify(calendarDTO);
    }
}
