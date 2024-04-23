package com.groovus.www.repository;

import com.groovus.www.entity.DriveBoard;
import com.groovus.www.repository.search.DriveBoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface DriveBoardRepository extends JpaRepository<DriveBoard, Long>, DriveBoardSearch {

    @Query(value = "SELECT now()", nativeQuery = true)
    String getTime();


}
