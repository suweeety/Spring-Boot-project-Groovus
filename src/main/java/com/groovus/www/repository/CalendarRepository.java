package com.groovus.www.repository;

import com.groovus.www.entity.Calendar;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    @EntityGraph(attributePaths = {"imageSet"})
    @Query("SELECT c FROM Calendar c WHERE c.cal_id =:cal_id")
    Optional<Calendar> findByIdWithImages(Long cal_id);

    // Project 삭제 시 일정들 삭제
    @Modifying
    @Query("DELETE FROM Calendar c WHERE c.cal_id =:cal_id")
    void deleteByCal_id(Long cal_id);

    // pid에 해당하는 일정 정보 가져와서 일정 아이디순으로 정렬
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("SELECT c FROM Calendar c WHERE c.project.pid =:pid ORDER BY c.cal_id")
    List<Calendar> getCalendarsByProjectOrderByCal_id(@Param("pid") Long pid);

    // 일정 아이디와 프로젝트 아이디로 하나의 일정 정보 가져오기
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("SELECT c FROM Calendar c WHERE c.cal_id =:cal_id AND c.project.pid =:pid")
    Optional<Calendar> findCalendarByCal_idAndProject(@Param("pid") Long pid, @Param("cal_id") Long cal_id);

    // mid로 멤버 정보 가져오기
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("SELECT m FROM Member m WHERE m.mid =:mid")
    Optional<Member> findByUsername(@Param("mid") Long mid);

    // pid와 cal_id로 초대 멤버 읽어오기
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("SELECT c.cal_members FROM Calendar c WHERE c.cal_id =:cal_id AND c.project.pid =:pid")
    List<Calendar> findCal_membersByCal_idAndProject(@Param("pid") Long pid, @Param("cal_id") Long cal_id);

    // 일정 개수
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("SELECT COUNT(c) FROM Calendar c WHERE c.project.pid=:pid")
    int countCalendarsByProject(@Param("pid") Long pid);

}
