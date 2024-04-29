package com.groovus.www.drive.service;

import com.groovus.www.service.DriveService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.groovus.www.dto.DriveDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.dto.PageResponseDTO;

@SpringBootTest
@Log4j2
public class DriveServiceTests {

    @Autowired
    private DriveService driveService;

    @Test
    public void testRegister(){

        log.info(driveService.getClass().getName());

        DriveDTO driveDTO = DriveDTO.builder()
                .title("Sample Title...")
                .nickname("user00")
                .build();

        Long bno = driveService.register(driveDTO);

        log.info("bno: " + bno);
    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<DriveDTO> responseDTO = driveService.list(pageRequestDTO);

        log.info(responseDTO);

    }

}

