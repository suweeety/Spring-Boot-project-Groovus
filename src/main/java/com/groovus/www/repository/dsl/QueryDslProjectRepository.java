package com.groovus.www.repository.dsl;

import com.groovus.www.dto.RegisterProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryDslProjectRepository {


    Page<RegisterProjectDTO> getProjectListWithMid(Long mid , Pageable pageable);



}
