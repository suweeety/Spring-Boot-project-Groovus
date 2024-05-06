package com.groovus.www.repository;

import com.groovus.www.entity.Calendar;
import com.groovus.www.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    @EntityGraph(attributePaths = {"imageSet"})
    @Query("SELECT c FROM Calendar c WHERE c.cal_id =:cal_id")
    Optional<Calendar> findByIdWithImages(Long cal_id);

    @EntityGraph(attributePaths = {"imageSet"})
    @Query("SELECT c FROM Calendar c WHERE c.cal_id = :cal_id")
    Optional<Calendar> findByCalId(Long cal_id);

    @EntityGraph(attributePaths = {"imageSet"})
    @Query("SELECT m FROM Member m WHERE m.mid =:mid")
    Optional<Member> findByUsername(@Param("mid") Long mid);

}
