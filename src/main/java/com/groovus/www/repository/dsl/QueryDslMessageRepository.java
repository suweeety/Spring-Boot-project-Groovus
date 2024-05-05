package com.groovus.www.repository.dsl;

import com.groovus.www.dto.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryDslMessageRepository {

    Page<MessageDTO> searchAll(String [] types, String keyword, Pageable pageable, Long pid,Long mid);

}
