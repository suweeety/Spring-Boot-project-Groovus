package com.groovus.www.drive.repository;

import com.groovus.www.entity.DriveBoard;
import com.groovus.www.repository.DriveBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class DriveBoardRepositoryTests {

    @Autowired
    private DriveBoardRepository driveBoardRepository;

    @Test
    public void insertBoard(){

        IntStream.rangeClosed(1,100).forEach(i -> {

            DriveBoard driveBoard = DriveBoard.builder()
                    .title("제목..."+i)
                    .nickname("닉네임..."+i)
                    .build();

            driveBoardRepository.save(driveBoard);
        });

    } // insertBoard Test

    @Test
    public void testRead1(){

        Optional<DriveBoard> result = driveBoardRepository.findById(10L);

        DriveBoard driveBoard = result.get();

        System.out.println(driveBoard);
        System.out.println(driveBoard.getNickname());
    } // testRead1

    @Test
    public void testUpdate(){

        Long bno = 1L;

        Optional<DriveBoard> result = driveBoardRepository.findById(bno);

        DriveBoard driveBoard = result.orElseThrow();

        driveBoard.change("수정된 제목");

        driveBoardRepository.save(driveBoard);
    } // testUpdate

    @Test
    public void testDelete(){
        Long bno = 1L;

        driveBoardRepository.deleteById(bno);
    } // delete

    @Test
    public void testPaging(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<DriveBoard> result = driveBoardRepository.findAll(pageable);
    } // testPaging

    @Test
    public void testSearch1(){

        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

        driveBoardRepository.search1(pageable);
    } // testSearch1

    @Test
    public void testSearchAll(){

        String[] types = {"t", "w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<DriveBoard> result = driveBoardRepository.searchAll(types, keyword, pageable);
    }

}
