package com.groovus.www.repository.dsl;

import com.groovus.www.dto.RegisterProjectDTO;
import com.groovus.www.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryDslProjectRepository {


    //mid로 모든 프로젝트 리스트를 반환하는 메서드
    List<RegisterProjectDTO> getProjectListWithMid(Long mid);

    //검색조건 및 페이징으로 프로젝트 목록을 반환하는 메서드
    Page<RegisterProjectDTO> searchAll(String [] types,String keyword, Pageable pageable,Long mid);


}
