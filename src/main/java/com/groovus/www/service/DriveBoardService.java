package com.groovus.www.service;

import com.groovus.www.dto.DriveBoardDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.dto.PageResponseDTO;
import com.groovus.www.entity.DriveBoard;

public interface DriveBoardService {

    Long register(DriveBoardDTO dto); // 등록

    DriveBoardDTO readOne(Long bno); // 읽기

    void modify(DriveBoardDTO driveBoardDTO); // 수정

    void remove(Long bno); // 삭제

    PageResponseDTO<DriveBoardDTO> list(PageRequestDTO pageRequestDTO);

    default DriveBoard dtoToEntity(DriveBoardDTO dto){

        DriveBoard driveBoard = DriveBoard.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .nickname(dto.getNickname())
                .build();

        return driveBoard;
    } // dtoToEntity

    default DriveBoardDTO entityToDTO(DriveBoard driveBoard){
        DriveBoardDTO driveBoardDTO = DriveBoardDTO.builder()
                .bno(driveBoard.getBno())
                .title(driveBoard.getTitle())
                .regdate(driveBoard.getRegDate())
                .moddate(driveBoard.getModDate())
                .nickname(driveBoard.getNickname())
                .build();

        return driveBoardDTO;
    } // entityToDTO




}
