package com.groovus.www.service;

import com.groovus.www.dto.DriveDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.dto.PageResponseDTO;

public interface DriveService {

    Long register(DriveDTO driveDTO);

    DriveDTO readOne(Long bno);

    void modify(DriveDTO driveDTO);

    void remove(Long bno);

    PageResponseDTO<DriveDTO> list(PageRequestDTO pageRequestDTO);
}
