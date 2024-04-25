package com.groovus.www.service;

import com.groovus.www.dto.DriveBoardDTO;
import com.groovus.www.dto.DriveFileDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.dto.PageResultDTO;
import com.groovus.www.entity.DriveBoard;
import com.groovus.www.entity.DriveFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface DriveBoardService {

    Long register(DriveBoardDTO dto); // 등록

    PageResultDTO<DriveBoardDTO, DriveBoard> getList(PageRequestDTO pageRequestDTO);

/*
    DriveBoardDTO readOne(Long bno); // 읽기
*/

/*    void modify(DriveBoardDTO driveBoardDTO); // 수정*/

    /*void remove(Long bno); // 삭제*/

    /*PageResponseDTO<DriveBoardDTO> list(PageRequestDTO pageRequestDTO);*/

    default Map<String, Object> dtoToEntity(DriveBoardDTO dto){

        Map<String, Object> entityMap = new HashMap<>();

        DriveBoard driveBoard = DriveBoard.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .nickname(dto.getNickname())
                .build();
        entityMap.put("driverBoard", driveBoard);

        List<DriveFileDTO> driveFileDTOList = dto.getDriveFileDTOList();

        if(driveFileDTOList != null && driveFileDTOList.size() > 0) {
            List<DriveFile> driveFileList = driveFileDTOList.stream()
                    .map(driveFileDTO -> {
                        DriveFile driveFile = DriveFile.builder()
                                .path(driveFileDTO.getPath())
                                .fName(driveFileDTO.getFName())
                                .uuid(driveFileDTO.getUuid())
                                .driveBoard(driveBoard)
                                .build();
                        return driveFile;
                    }).collect(Collectors.toList());

                    entityMap.put("fileList", driveFileList);
        }
        return entityMap;
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
