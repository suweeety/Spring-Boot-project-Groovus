package com.groovus.www.repository.dsl;

import com.groovus.www.dto.notice.NoticeResponseDTO;
import com.groovus.www.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryDslNoticeRepository {

    Page<NoticeResponseDTO> searchAll(String[] types, String keyword, Pageable pageable);

}
