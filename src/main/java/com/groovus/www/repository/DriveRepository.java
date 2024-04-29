package com.groovus.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.groovus.www.entity.Drive;
import com.groovus.www.repository.search.DriveSearch;

public interface DriveRepository extends JpaRepository<Drive, Long>, DriveSearch {

    @Query(value = "SELECT now()", nativeQuery = true)
    String getTime();
}
