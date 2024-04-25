package com.groovus.www.service;

import com.groovus.www.dto.DriveBoardDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.dto.PageResultDTO;
import com.groovus.www.entity.DriveBoard;
import com.groovus.www.entity.DriveFile;
import com.groovus.www.entity.QDriveBoard;
import com.groovus.www.repository.DriveBoardRepository;
import com.groovus.www.repository.DriveFileRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
@Log4j2
public class DriveBoardServiceImpl implements DriveBoardService {

    private final DriveBoardRepository repository;

    private final DriveFileRepository fileRepository;

    @Override
    public Long register(DriveBoardDTO dto) {

        log.info(dto);

        Map<String, Object> entityMap = dtoToEntity(dto);

        DriveBoard driveBoard = (DriveBoard) entityMap.get("driveBoard");

        List<DriveFile> driveFileList = (List<DriveFile>) entityMap.get("fileList");

        repository.save(driveBoard);

        driveFileList.forEach(driveFile -> {
            fileRepository.save(driveFile);
        });

        return driveBoard.getBno();

           } // register

    @Override
    public PageResultDTO<DriveBoardDTO, DriveBoard> getList(PageRequestDTO requestDTO){

        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Page<DriveBoard> result = repository.findAll(booleanBuilder, pageable);

        Function<DriveBoard, DriveBoardDTO> fn = (driveBoard -> entityToDTO(driveBoard));

        return new PageResultDTO<>(result, fn);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){

        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QDriveBoard qDriveBoard = QDriveBoard.driveBoard;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qDriveBoard.bno.gt(0L);

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0){
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){
            conditionBuilder.or(qDriveBoard.title.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qDriveBoard.nickname.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
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
