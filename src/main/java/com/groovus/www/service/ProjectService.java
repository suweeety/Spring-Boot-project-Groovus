package com.groovus.www.service;

import com.groovus.www.dto.*;
import com.groovus.www.entity.Member;
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
    public ProjectPageResponseDTO<RegisterProjectDTO> getProjectListWithPaging(ProjectPageRequestDTO pageRequestDTO ,Long mid);

    //해당 프로젝트에 속한 멤버를 가져오는 메서드
    public List<MemberInfoDTO> getProjectMembers(String pid);

    //프로젝트DTO 반환 메서드
    RegisterProjectDTO getProjectDTO(Long pid);

    //프로젝트 설명 변경
    boolean changeDes(Long pid, String des);

    //프로젝트 비밀번호 변경
    boolean changePw(Long pid , String pw);

    //프로젝트 멤버 초대
    boolean addMember(Long pid , List<String> newMembers);

    //프로젝트 멤버 제외
    boolean deleteMember(Long pid,String deleteMember);

}
