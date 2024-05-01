package com.groovus.www.repository;

import com.groovus.www.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    //모든 공지사항 찾는 메서드(pid 기준,작성일 내림차순) List
    List<Notice> findAllByProject_PidOrderByRegDateDesc(Long pid);

    //공지사항 찾는 메서드(pid 기준)
    Notice findByProjectPid(Long pid);

    //첫번째 공지사항 찾는 메서드(pid 기준,작성일 오름차순)
    Optional<Notice> findFirstByProjectPidOrderByRegDateAsc(Long pid);

    //첫번째 공지사항 찾는 메서드(pid 기준,작성일 내림차순)
    Optional<Notice> findFirstByProjectPidOrderByRegDateDesc(Long pid);

    //주어진 id 이전의 모든 공지사항을 페이징하는 메서드(pid 기준,작성일 내림차순)
    Slice<Notice> findAllByNidBeforeAndProject_PidOrderByRegDateDesc(Long cursor, Long pid, Pageable pageable);

    //모든 공지사항 찾는 메서드(pid 기준,작성일 내림차순) Optional
    Optional<Notice> findAllByProjectPidOrderByRegDateDesc(Long pid);

}
