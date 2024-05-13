package com.groovus.www.service;

import com.groovus.www.dto.DriveDTO;
import com.groovus.www.dto.DriveImageDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.dto.PageResultDTO;
import com.groovus.www.entity.Drive;
import com.groovus.www.entity.DriveImage;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface DriveService {

    Long register(DriveDTO driveDTO);

    DriveDTO getDrive(Long bno);

    void modify(DriveDTO driveDTO);

//    void moveToBin(Long bno);



    PageResultDTO<DriveDTO, Object[]> getList(PageRequestDTO requestDTO);

    default DriveDTO entitiesToDTO(Drive drive, List<DriveImage> driveImages){
        DriveDTO driveDTO = DriveDTO.builder()
                .bno(drive.getBno())
                .title(drive.getTitle())
                .regDate(drive.getRegDate())
                .modDate(drive.getModDate())
                .build();

        List<DriveImageDTO> driveImageDTOList = driveImages.stream().map(driveImage -> {
            return DriveImageDTO.builder().imgName(driveImage.getImgName())
                    .path(driveImage.getPath())
                    .uuid(driveImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        driveDTO.setImageDTOList(driveImageDTOList);



        return driveDTO;

    }
    default Map<String, Object> dtoToEntity(DriveDTO driveDTO){

        Map<String, Object> entityMap = new HashMap<>();

        Drive drive = Drive.builder().
                bno(driveDTO.getBno()).
                title(driveDTO.getTitle()).build();

        entityMap.put("drive", drive);

        List<DriveImageDTO> imageDTOList = driveDTO.getImageDTOList();

        if(imageDTOList != null && imageDTOList.size() > 0) {
            List<DriveImage> driveImageList = imageDTOList.stream().map(driveImageDTO ->
                    {
                        DriveImage driveImage = DriveImage.builder()
                            .path(driveImageDTO.getPath())
                            .imgName(driveImageDTO.getImgName())
                            .uuid(driveImageDTO.getUuid())
                            .drive(drive).build();
                    return driveImage;
                    }).collect(Collectors.toList());  //객체를 새로운 리스트로 만드는 방법
            entityMap.put("imgList", driveImageList);

        }
        return entityMap;
    }
}
