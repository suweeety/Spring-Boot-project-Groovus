package com.groovus.www.drive.repository;

import com.groovus.www.entity.Drive;
import com.groovus.www.entity.DriveImage;
import com.groovus.www.repository.DriveImageRepository;
import com.groovus.www.repository.DriveRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class DriveRepositoryTests {

    @Autowired
    private DriveRepository driveRepository;

    @Autowired
    private DriveImageRepository imageRepository;

    @Commit
    @Transactional
    @Test
    public void insertDrives() {

        IntStream.rangeClosed(1,5).forEach(i -> {

            Drive drive = Drive.builder().title("drive...." +i).build();

            System.out.println("------------------------------------------");

            driveRepository.save(drive);

            int count = (int)(Math.random() * 5) + 1; //1,2,3,4


            for(int j = 0; j < count; j++){
                DriveImage driveImage = DriveImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .drive(drive)
                        .imgName("test"+j+".jpg").build();

                imageRepository.save(driveImage);
            }


            System.out.println("===========================================");

        });
    }

    @Test
    public void testListPage(){

        PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "mno"));

        Page<Object[]> result = driveRepository.getListPage(pageRequest);

        for (Object[] objects : result.getContent()) {
            System.out.println(Arrays.toString(objects));
        }

    }

    @Test
    public void testGetDriveWithAll() {

        List<Object[]> result = driveRepository.getDriveWithAll(92L);

        System.out.println(result);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }

    }

}

