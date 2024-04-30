package com.groovus.www.repository.dsl;

import com.groovus.www.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryDslTaskRepository {

    Page<TaskDTO> searchAll(String [] types, String keyword, Pageable pageable, Long pid);
}
