package com.groovus.www.repository;

import com.groovus.www.entity.DriveBoard;
import com.groovus.www.repository.search.DriveBoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DriveBoardRepository extends JpaRepository<DriveBoard, Long>, DriveBoardSearch {

    List<DriveBoard> findByBnoBetweenOrderByBnoDesc(Long from, Long to);

    Page<DriveBoard> findByBnoBetween(Long from, Long to, Pageable pageable);

    void deleteDriveBoardByBnoLessThan(Long num);

    @Query("SELECT d, df " +
           " FROM DriveBoard d LEFT OUTER JOIN DriveFile df ON df.driveBoard = d " +
            " WHERE d.bno = :bno")
    List<Object[]> getDriveWithAll(Long bno);

}
