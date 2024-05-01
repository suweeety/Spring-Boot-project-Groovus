package com.groovus.www.service;

import com.groovus.www.dto.ProjectPageRequestDTO;
import com.groovus.www.dto.ProjectPageResponseDTO;
import com.groovus.www.dto.RegisterProjectDTO;
import com.groovus.www.dto.RegisterProjectRequestDTO;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProjectService {

    public boolean registerProject(RegisterProjectRequestDTO projectDTO);

    //회원이 속해있는 프로젝트 리스트를 가져오는 메서드
    public List<RegisterProjectDTO> getProjectList(Long mid);

    //해당 프로젝트 이동전 비밀번호가 일치하는지 확인하는 메서드
    public boolean validProjectPw(String pid, String pw);

    //프로젝트를 삭제하는 메서드
    public boolean deleteProject(String pid);


    //프로젝트리스트를 가져오는 메서드 (페이징)
    ProjectPageResponseDTO<RegisterProjectDTO> getProjectListWithPaging(ProjectPageRequestDTO pageRequestDTO ,Long mid);


}
