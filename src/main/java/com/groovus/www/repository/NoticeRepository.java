package com.groovus.www.repository;

import com.groovus.www.entity.Notice;
import com.groovus.www.repository.dsl.QueryDslNoticeRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long>, QueryDslNoticeRepository {

    //프로젝트 번호로 공지사항 목록을 가져오는 쿼리
    @EntityGraph(attributePaths = "project")
    @Query("select n from Notice n where n.project.pid=:pid")
    List<Notice> getNoticeByPid(Long pid);

    //pid와 nid로 가져오는 쿼리
    @EntityGraph(attributePaths = "project")
    @Query("select n from Notice n where n.project.pid=:pid and n.nid=:nid")
    Optional<Notice> getNoticeByPidandNid(Long pid, Long nid);

    @EntityGraph(attributePaths = "project")
    @Query("select count(n) from Notice n where n.project.pid=:pid and n.del=false")
    Long countNotice(Long pid);

    //삭제업무 개수
    @EntityGraph(attributePaths = "project")
    @Query("select count(n) from Notice n where n.project.pid=:pid and n.del=true and n.noticeWriter=:uid")
    Long countDelNotice(Long pid , String uid);


    @EntityGraph(attributePaths = "project")
    @Query("select n from Notice n where n.nid=:nid and n.del=false")
    Optional<Notice> getNoticeByNid(Long nid);

    //작성자 아이디로 업무를 가져오는 메서드
    @EntityGraph(attributePaths = "project")
    @Query("select n from Notice n where n.noticeWriter=:noticeWriter")
    List<Notice> getNoticeByNoticeWriter(String noticeWriter);

}