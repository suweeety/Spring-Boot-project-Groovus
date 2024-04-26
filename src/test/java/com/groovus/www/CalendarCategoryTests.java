package com.groovus.www;

import com.groovus.www.dto.CalendarCategoryDTO;
import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.service.CalendarCategoryService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class CalendarCategoryTests {

    @Autowired
    private CalendarCategoryService calendarCategoryService;

    @Test
    public void testRegister() {
        log.info(calendarCategoryService.getClass().getName());

        CalendarCategoryDTO calendarCategoryDTO = CalendarCategoryDTO.builder()
                .cal_category("팀 회의")
                .build();
        Long calCate_id = calendarCategoryService.register(calendarCategoryDTO);

        log.info("calCate_id: "+ calCate_id);
    }
}
