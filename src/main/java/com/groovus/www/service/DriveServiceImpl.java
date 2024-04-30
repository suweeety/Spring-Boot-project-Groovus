package com.groovus.www.service;

import com.groovus.www.dto.DriveAllDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.groovus.www.entity.Drive;
import com.groovus.www.dto.DriveDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.dto.PageResponseDTO;
import com.groovus.www.repository.DriveRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class DriveServiceImpl implements DriveService{

    private final ModelMapper modelMapper;

    private final DriveRepository driveRepository;

    @Override
    public Long register(DriveDTO driveDTO) {

        Drive drive = dtoToEntity(driveDTO);

        Long bno = driveRepository.save(drive).getBno();

        return bno;
//        Drive drive = modelMapper.map(driveDTO, Drive.class);
//
//        Long bno = driveRepository.save(drive).getBno();
//
//        return bno;
    }

    @Override
    public DriveDTO readOne(Long bno) {

        Optional<Drive> result = driveRepository.findByIdWithImages(bno);

        Drive drive = result.orElseThrow();

        // DriveDTO driveDTO = modelMapper.map(drive, DriveDTO.class);

        DriveDTO driveDTO = entityToDTO(drive);

        return driveDTO;
    }

    @Override
    public void modify(DriveDTO driveDTO) {

        Optional<Drive> result = driveRepository.findById(driveDTO.getBno());

        Drive drive = result.orElseThrow();

        drive.change(driveDTO.getTitle());

        drive.clearImages();

        if(driveDTO.getFileNames() != null){
            for (String fileName : driveDTO.getFileNames()) {
                String[] arr = fileName.split("_");
                drive.addImage(arr[0], arr[1]);
            }
        }
        driveRepository.save(drive);
    }

    @Override
    public void remove(Long bno) {

        driveRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<DriveDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Drive> result = driveRepository.searchAll(types, keyword, pageable);

        List<DriveDTO> dtoList = result.getContent().stream()
                .map(drive -> modelMapper.map(drive,DriveDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<DriveDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();

    }

//    @Override
//    public PageResponseDTO<DriveAllDTO> listWithALl(PageRequestDTO pageRequestDTO) {
//        String[] types = pageRequestDTO.getTypes();
//        String keyword = pageRequestDTO.getKeyword();
//        Pageable pageable = pageRequestDTO.getPageable("bno");
//
//        Page<DriveAllDTO> result = driveRepository.searchAll(types, keyword, pageable);
//
//        return PageResponseDTO.<DriveAllDTO>withAll()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(result.getContent())
//                .total((int)result.getTotalElements())
//                .build();
//    }

}
