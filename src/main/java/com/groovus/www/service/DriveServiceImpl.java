package com.groovus.www.service;

import com.groovus.www.entity.Drive;
import com.groovus.www.entity.DriveImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.groovus.www.dto.DriveDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.dto.PageResultDTO;
import com.groovus.www.repository.DriveImageRepository;
import com.groovus.www.repository.DriveRepository;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class DriveServiceImpl implements DriveService {

    private final DriveRepository driveRepository;

    private final DriveImageRepository ImageRepository;

    @Transactional
    @Override
    public Long register(DriveDTO driveDTO) {

        Map<String , Object> entityMap = dtoToEntity(driveDTO);
        Drive drive =(Drive) entityMap.get("drive");
        List<DriveImage> driveImageList= (List<DriveImage>) entityMap.get("imgList");

        driveRepository.save(drive);

        driveImageList.forEach(driveImage -> {
           ImageRepository.save(driveImage);

        });
        return drive.getBno();
    }

    @Override
    public DriveDTO getDrive(Long bno) {
        List<Object[]> result = driveRepository.getDriveWithAll(bno);

        Drive drive = (Drive) result.get(0)[0];

        List<DriveImage> driveImageList = new ArrayList<>();

        result.forEach(arr -> {
            DriveImage  driveImage = (DriveImage)arr[1];
            driveImageList.add(driveImage);
        });


        return entitiesToDTO(drive, driveImageList);
    }

    @Override
    public void modify(DriveDTO driveDTO) {

        Drive drive = driveRepository.getOne(driveDTO.getBno());

        if(drive != null) {

            drive.changeTitle(driveDTO.getTitle());

            driveRepository.save(drive);
        }
    }


    @Override
    public PageResultDTO<DriveDTO, Object[]> getList(PageRequestDTO requestDTO) {
          Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());

        Page<Object[]> result = driveRepository.getListPage(pageable);

        log.info("==============================================");
        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
        });


        Function<Object[], DriveDTO> fn = (arr -> entitiesToDTO(
                (Drive)arr[0] ,
                (List<DriveImage>)(Arrays.asList((DriveImage)arr[1]))

        ));

        return new PageResultDTO<>(result, fn);
    }
}
