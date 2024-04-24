package com.groovus.www.drive.repository;

import com.groovus.www.entity.DriveBoard;
import com.groovus.www.entity.DriveFile;
import com.groovus.www.repository.DriveBoardRepository;
import com.groovus.www.repository.DriveFileRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class DriveBoardRepositoryTests {

    @Autowired
    private DriveBoardRepository driveBoardRepository;

    @Autowired
    protected DriveFileRepository driveFileRepository;
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

    @Test
    public void testPageDefault(){
        Pageable pageable = PageRequest.of(0, 10);

        Page<DriveBoard> result = driveBoardRepository.findAll(pageable);
        System.out.println(result);

        System.out.println("---------------------------------------");

        System.out.println("Total Pages: "+result.getTotalPages()); // 총 몇 페이지

        System.out.println("Total Count: "+result.getTotalElements()); // 전체 개수

        System.out.println("Page Number: "+result.getNumber()); // 현재 페이지 번호

        System.out.println("Page Size: "+result.getSize()); // 페이지당 데이터 개수

        System.out.println("has next page?: "+result.hasNext());    // 다음 페이지 존재 여부

        System.out.println("first page?: "+result.isFirst());  //  시작페이지 여부


        System.out.println("-----------------------------------");

        for(DriveBoard driveBoard : result.getContent()) {
            System.out.println(driveBoard);

        }

    } // testPageDefault

    @Test
    public void testQueryMethods(){

        List<DriveBoard> list = driveBoardRepository.findByBnoBetweenOrderByBnoDesc(70L, 80L);

        for (DriveBoard driveBoard : list){
            System.out.println(driveBoard);
        }
    } // testQueryMethods

    @Test
    @Commit
    @Transactional
    public void insertFile(){

        IntStream.rangeClosed(1,100).forEach(i -> {

            DriveBoard driveBoard = DriveBoard.builder().title("드라이브..." + i).build();
            System.out.println("--------------------");
            driveBoardRepository.save(driveBoard);
            int count = (int)(Math.random() * 5) + 1;

            for (int j = 0; j < count; j++){
                DriveFile driveFile = DriveFile.builder()
                        .uuid(UUID.randomUUID().toString())
                        .driveBoard(driveBoard)
                        .fName("테스트"+j+".jpg")
                        .build();

                driveFileRepository.save(driveFile);
            }
            System.out.println("-------------------");
        });
    } // insertFile

    @Test
    public void testGetDriveWithAll(){

        List<Object[]> result = driveBoardRepository.getDriveWithAll(242L);
        System.out.println(result);

        for(Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }

}
