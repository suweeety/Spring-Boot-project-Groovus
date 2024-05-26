package com.groovus.www.service;


import com.groovus.www.dto.DriveDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.dto.PageResponseDTO;
import com.groovus.www.entity.Drive;

import java.util.List;
import java.util.stream.Collectors;

public interface DriveService {

    Long register(DriveDTO driveDTO);

    DriveDTO readOne(Long bno);

    void modify(DriveDTO driveDTO);

    void remove(Long bno);

    PageResponseDTO<DriveDTO> list(PageRequestDTO pageRequestDTO);

    //   PageResponseDTO<DriveAllDTO> listWithALl(PageRequestDTO pageRequestDTO);

    default Drive dtoToEntity(DriveDTO driveDTO){

        Drive drive = Drive.builder()
                .bno(driveDTO.getBno())
                .title(driveDTO.getTitle())
                .nickname(driveDTO.getNickname())
                .build();

        if (driveDTO.getFileNames() != null){
            driveDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                drive.addImage(arr[0], arr[1]);
            });
        }
        return drive;
    } // dtoToEntity

    default DriveDTO entityToDTO(Drive drive){

        DriveDTO driveDTO = DriveDTO.builder()
                .bno(drive.getBno())
                .title(drive.getTitle())
                .nickname(drive.getNickname())
                .regdate(drive.getRegDate())
                .moddate(drive.getModDate())
                .build();

        List<String> fileNames =
                drive.getImageSet().stream().sorted().map(driveImage ->
                        driveImage.getUuid()+"_"+driveImage.getFileName()).collect(Collectors.toList());

        driveDTO.setFileNames(fileNames);

        return driveDTO;
    }
}