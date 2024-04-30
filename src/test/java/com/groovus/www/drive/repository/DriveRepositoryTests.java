package com.groovus.www.drive.repository;

import com.groovus.www.entity.DriveImage;
import com.groovus.www.repository.DriveRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.groovus.www.entity.Drive;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class DriveRepositoryTests {

    @Autowired
    private DriveRepository driveRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Drive drive = Drive.builder()
                    .title("title..." + i)
                    .nickname("user" + (i % 10))
                    .build();

            Drive result = driveRepository.save(drive);
            log.info("BNO: " + result.getBno());
        });
    } // testInsert

    @Test
    public void testSelect() {
        Long bno = 100L;

        Optional<Drive> result = driveRepository.findById(bno);

        Drive drive = result.orElseThrow();

        log.info(drive);

    } // testSelect

    @Test
    public void testUpdate() {

        Long bno = 100L;

        Optional<Drive> result = driveRepository.findById(bno);

        Drive drive = result.orElseThrow();

        drive.change("update..title 100");

        driveRepository.save(drive);

    } // testUpdate

    @Test
    public void testDelete() {
        Long bno = 1L;

        driveRepository.deleteById(bno);
    } // testDelete

    @Test
    public void testPaging() {

        //1 page order by bno desc
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Drive> result = driveRepository.findAll(pageable);


        log.info("total count: "+result.getTotalElements());
        log.info( "total pages:" +result.getTotalPages());
        log.info("page number: "+result.getNumber());
        log.info("page size: "+result.getSize());

        List<Drive> todoList = result.getContent();

        todoList.forEach(drive -> log.info(drive));


    } // testPaging

    @Test
    public void testSearch1() {

        //2 page order by bno desc
        Pageable pageable = PageRequest.of(1,10, Sort.by("bno").descending());

        driveRepository.search1(pageable);

    } // testSearch1

    @Test
    public void testSearchAll(){

        String[] types = {"t", "w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Drive> result = driveRepository.searchAll(types, keyword, pageable);

        log.info(result.getTotalPages());

        log.info(result.getSize());

        log.info(result.hasPrevious() + ": " + result.hasNext());

        result.getContent().forEach(drive -> log.info(drive));
    } // testSearchAll

    @Test
    public void testInsertWithImages(){

        Drive drive = Drive.builder()
                .title("파일테스트")
                .nickname("닉네임")
                .build();
        for (int i = 0; i < 3; i++){

            drive.addImage(UUID.randomUUID().toString(), "file"+i+".jpg");
        }

        driveRepository.save(drive);
    } // testInsertWithImages

    @Transactional
    @Test
    public void testReadWithImages(){

        Optional<Drive> result = driveRepository.findById(205L);

        Drive drive = result.orElseThrow();

        log.info(drive);
        log.info("---------------------");
        for (DriveImage driveImage : drive.getImageSet()) {
            log.info(driveImage);
        }
    } // testReadWithImages

    @Transactional
    @Commit
    @Test
    public void testModifyImages(){

        Optional<Drive> result = driveRepository.findByIdWithImages(205L);

        Drive drive = result.orElseThrow();

        drive.clearImages();

        for (int i = 0; i < 2; i++){

            drive.addImage(UUID.randomUUID().toString(), "updatefile"+i+".jpg");
        }

        driveRepository.save(drive);
    } // testModifyImages

    @Test
    public void testInsertAll(){

        for (int i = 1; i <= 20; i++){

            Drive drive = Drive.builder()
                    .title("Title..."+i)
                    .nickname("nickname..."+i)
                    .build();

            for (int j = 0; j < 3; j++){

                if(i % 5 == 0 ){
                    continue;
                }
                drive.addImage(UUID.randomUUID().toString(), i+"file"+j+".jpg");
            }
            driveRepository.save(drive);
        }
    } // testInsertALl

}