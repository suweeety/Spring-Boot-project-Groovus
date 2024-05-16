
package com.groovus.www.repository;

import com.groovus.www.entity.Drive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DriveRepository extends JpaRepository<Drive, Long> {



    @Query("select d, di from Drive d " +
            "left outer join DriveImage di on di.drive = d ")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select d, di from Drive d " +
            "left outer join DriveImage di on di.drive = d " +
            "where d.bno = :bno")
    List<Object[]> getDriveWithAll(Long bno);
}
