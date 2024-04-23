package com.groovus.www.drive.service;

import com.groovus.www.dto.DriveBoardDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.dto.PageResponseDTO;
import com.groovus.www.service.DriveBoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class DriveBoardServiceTests {

    @Autowired
    private DriveBoardService driveBoardService;

    @Test
    public void testRegister(){

        log.info(driveBoardService.getClass().getName());

        DriveBoardDTO driveBoardDTO = DriveBoardDTO.builder()
                .title("제목!")
                .nickname("닉네임!")
                .build();

        Long bno = driveBoardService.register(driveBoardDTO);

        log.info("bno: " + bno);
    } // testRegister

    @Test
    public void testModify(){

        DriveBoardDTO driveBoardDTO = DriveBoardDTO.builder()
                .bno(101L)
                .title("수정제목!")
                .build();
        driveBoardService.modify(driveBoardDTO);
    } // testModify

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tw")
                .keyword("제목")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<DriveBoardDTO> responseDTO = driveBoardService.list(pageRequestDTO);

        log.info(responseDTO);
    } // testList 안됨 ㅠ_ㅠ


}
