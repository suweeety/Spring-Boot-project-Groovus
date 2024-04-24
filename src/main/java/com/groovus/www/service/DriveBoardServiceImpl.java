package com.groovus.www.service;

import com.groovus.www.dto.DriveBoardDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.dto.PageResultDTO;
import com.groovus.www.entity.DriveBoard;
import com.groovus.www.repository.DriveBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.function.Function;


@Service
@RequiredArgsConstructor
@Log4j2
public class DriveBoardServiceImpl implements DriveBoardService {

    private final DriveBoardRepository repository;

    @Override
    public Long register(DriveBoardDTO dto) {

        log.info(dto);

        DriveBoard driveBoard = dtoToEntity(dto);

        repository.save(driveBoard);

        return driveBoard.getBno();

           } // register

    @Override
    public PageResultDTO<DriveBoardDTO, DriveBoard> getList(PageRequestDTO requestDTO){
        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());
        Page<DriveBoard> result = repository.findAll(pageable);
        Function<DriveBoard, DriveBoardDTO> fn = (driveBoard -> entityToDTO(driveBoard));
        return new PageResultDTO<>(result, fn);
    }

 /*   @Override
    public DriveBoardDTO readOne(Long bno) {

        Optional<DriveBoard> result = driveBoardRepository.findById(bno);

        DriveBoard driveBoard = result.orElseThrow();

        DriveBoardDTO driveBoardDTO = modelMapper.map(driveBoard, DriveBoardDTO.class);

        return driveBoardDTO;
    } // readOne*/

    /*@Override
    public void modify(DriveBoardDTO driveBoardDTO) {

        Optional<DriveBoard> result = driveBoardRepository.findById(driveBoardDTO.getBno());

        DriveBoard driveBoard = result.orElseThrow();

        driveBoard.change(driveBoardDTO.getTitle());

        driveBoardRepository.save(driveBoard);
    } // modify*/

    /*@Override
    public void remove(Long bno) {

        driveBoardRepository.deleteById(bno);
    } // remove
*/
  /*  @Override
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
*/

}
