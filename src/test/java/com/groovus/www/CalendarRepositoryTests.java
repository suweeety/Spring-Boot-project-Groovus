package com.groovus.www;

import com.groovus.www.entity.Calendar;
import com.groovus.www.repository.CalendarAttachRepository;
import com.groovus.www.repository.CalendarRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class CalendarRepositoryTests {

    @Autowired
    CalendarRepository calendarRepository;

    @Test
    public void testInsertWithImages() {
        Calendar calendar = Calendar.builder()
                .cal_title("Image Test")
                .cal_content("첨부파일 테스트")
                .build();

        for(int i=0; i<3; i++) {
            calendar.addImage(UUID.randomUUID().toString(), "file"+i+".jpg");
        }
        calendarRepository.save(calendar);
    }

    @Test
    public void testGetCalendarByCal_idAndAndProject() {
        Optional<Calendar> result = calendarRepository.findCalendarByCal_idAndProject(65L,44L);
        log.info("++++++++++++++++++++++++++"+result);
    }
}
