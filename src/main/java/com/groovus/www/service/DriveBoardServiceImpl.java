package com.groovus.www.service;

import com.groovus.www.dto.DriveBoardDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.dto.PageResponseDTO;
import com.groovus.www.entity.DriveBoard;
import com.groovus.www.repository.DriveBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Log4j2
public class DriveBoardServiceImpl implements DriveBoardService {

    private final ModelMapper modelMapper;
    private final DriveBoardRepository driveBoardRepository;

    @Override
    public Long register(DriveBoardDTO driveBoardDTO) {

        DriveBoard driveBoard = modelMapper.map(driveBoardDTO, DriveBoard.class);

        Long bno = driveBoardRepository.save(driveBoard).getBno();

        return driveBoard.getBno();
    }

    @Override
    public DriveBoardDTO readOne(Long bno) {

        Optional<DriveBoard> result = driveBoardRepository.findById(bno);

        DriveBoard driveBoard = result.orElseThrow();

        DriveBoardDTO driveBoardDTO = modelMapper.map(driveBoard, DriveBoardDTO.class);

        return driveBoardDTO;
    } // readOne

    @Override
    public void modify(DriveBoardDTO driveBoardDTO) {

        Optional<DriveBoard> result = driveBoardRepository.findById(driveBoardDTO.getBno());

        DriveBoard driveBoard = result.orElseThrow();

        driveBoard.change(driveBoardDTO.getTitle());

        driveBoardRepository.save(driveBoard);
    } // modify

    @Override
    public void remove(Long bno) {

        driveBoardRepository.deleteById(bno);
    } // remove

    @Override
    public PageResponseDTO<DriveBoardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<DriveBoard> result = driveBoardRepository.searchAll(types, keyword, pageable);

        List<DriveBoardDTO> dtoList = result.getContent().stream()
                .map(driveBoard -> modelMapper.map(driveBoard, DriveBoardDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<DriveBoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }


}
