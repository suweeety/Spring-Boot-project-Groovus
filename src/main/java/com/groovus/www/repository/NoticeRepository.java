package com.groovus.www.repository;

import com.groovus.www.dto.notice.NoticeDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<NoticeDTO, Long> {
}
