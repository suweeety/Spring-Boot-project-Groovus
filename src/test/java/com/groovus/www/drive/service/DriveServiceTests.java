package com.groovus.www.drive.service;

import com.groovus.www.service.DriveService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.groovus.www.dto.DriveDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.dto.PageResponseDTO;

import java.util.Arrays;
import java.util.UUID;

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

    @Test
    public void testRegisterWithImages(){

        log.info(driveService.getClass().getName());

        DriveDTO driveDTO = DriveDTO.builder()
                .title("File...Sample Title...")
                .nickname("user00")
                .build();

        driveDTO.setFileNames(
                Arrays.asList(
                        UUID.randomUUID()+"_aaa.jpg",
                        UUID.randomUUID()+"_bbb.jpg",
                        UUID.randomUUID()+"_ccc.jpg"
                )
        );

        Long bno = driveService.register(driveDTO);

        log.info("bno: " + bno);
    } // testRegisterWithImages

    @Test
    public void testReadALl(){

        Long bno = 226L;

        DriveDTO driveDTO = driveService.readOne(bno);

        log.info(driveDTO);

        for (String fileName : driveDTO.getFileNames()) {
            log.info(fileName);
        }
    } // testReadALl

    @Test
    public void testModify(){

        DriveDTO driveDTO = DriveDTO.builder()
                .bno(206L)
                .title("업데이트 101")
                .build();

        driveDTO.setFileNames(Arrays.asList(UUID.randomUUID()+"_zzz.jpg"));

        driveService.modify(driveDTO);
    } // testModify

    @Test
    public void testRemoveAll(){

        Long bno = 224L;

        driveService.remove(bno);
    }
}

