package com.groovus.www.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.groovus.www.entity.Drive;
import com.groovus.www.repository.search.DriveSearch;

import java.util.Optional;

public interface DriveRepository extends JpaRepository<Drive, Long>, DriveSearch {

//    @Query(value = "SELECT now()", nativeQuery = true)
//    String getTime();

    @EntityGraph(attributePaths = {"imageSet"}) // attributePaths 같이 로딩해야 하는 속성을 명시함.
    @Query("select d from Drive d where d.bno =:bno")
    Optional<Drive> findByIdWithImages(Long bno); // bno를 이용해 이미지를 찾아옴.
}
