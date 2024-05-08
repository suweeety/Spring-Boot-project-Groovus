package com.groovus.www.repository;

import com.groovus.www.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    // 작업 공간 관련 정보를 제외하고 생성일자를 기준으로 내림차순으로 모든 공지사항을 가져옴
    List<Notice> findAllByOrderByRegDateDesc();

    // 생성일자를 기준으로 오름차순으로 첫 번째 공지사항을 가져옴
    Notice findFirstByOrderByRegDateAsc();
    // 생성일자를 기준으로 내림차순으로 첫 번째 공지사항을 가져옴
    Notice findFirstByOrderByRegDateDesc();

    // 주어진 커서 이전의 공지사항을 생성일자를 기준으로 내림차순으로 가져옴
    Slice<Notice> findAllByNidBeforeOrderByRegDateDesc(Long cursor, Pageable pageable);

}
