package com.groovus.www.service;

import com.groovus.www.dto.ProjectPageRequestDTO;
import com.groovus.www.dto.ProjectPageResponseDTO;
import com.groovus.www.dto.RegisterProjectDTO;
import com.groovus.www.dto.RegisterProjectRequestDTO;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Project;
import com.groovus.www.repository.MemberRepository;
import com.groovus.www.repository.ProjectRepository;
import groovyjarjarpicocli.CommandLine;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public boolean registerProject(RegisterProjectRequestDTO projectDTO) {
        //프로젝트를 등록

        Project project = Project.builder()
                .projectName(projectDTO.getProjectName())
                .adminUid(projectDTO.getAdminUid())
                .projectDescription(projectDTO.getProjectDescription())
                .projectPassword(projectDTO.getProjectPassword())
                .build();

        for(String memberUid : projectDTO.getProjectMember()){

            Optional<Member> result = memberRepository.findByUid(memberUid);

            if(!result.isEmpty()){

                Member member = result.get();
                project.addMember(member);
            }
        }

        projectRepository.save(project);

        return true;

    }

    @Override
    public List<RegisterProjectDTO> getProjectList(Long mid) {
            //멤버가 속해있는 프로젝트 목록을 가져오는 메서드
            //멤버 시퀀스 mid와 Pageable변수를 받아 해당 멤버가 속해있는 프로젝트 리스트를 반환한다.
        List<RegisterProjectDTO> result = projectRepository.getProjectListWithMid(mid);

        return result;
    }

    @Override
    public boolean validProjectPw(String pid, String pw) {
        //프로젝트 이동 전 패스워드를 확인함
        //프로젝트 삭제 전 패스워드를 확인함

        Optional<Project> result = projectRepository.findByIdWithMember(Long.parseLong(pid));

        if(result.isEmpty()){
            //프로젝트가 존재하지 않는다면
            return false;
        }else {
            //프로젝트가 존재한다면
            Project project = result.get();

            if(project.getProjectPassword().equals(pw)){
                //프로젝트 비밀번호가 입력받은 pw와 동일하다면
                return true;
            }else{
                //프로젝트 비밀번호가 일치하지 않는다면
                return false;
            }
        }
    }

    @Override
    @Transactional
    public boolean deleteProject(String pid) {

        projectRepository.deleteProject(Long.parseLong(pid));


        return false;
    }

    @Override
    @Transactional
    public ProjectPageResponseDTO<RegisterProjectDTO> getProjectListWithPaging(ProjectPageRequestDTO pageRequestDTO, Long mid) {

        String [] types= pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("pid");

        Page<RegisterProjectDTO> result = projectRepository.searchAll(types,keyword,pageable,mid);

        List<RegisterProjectDTO> resultList = result.stream().collect(Collectors.toList());

        return ProjectPageResponseDTO.<RegisterProjectDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(resultList)
                .total((int)result.getTotalElements())
                .build();
    }


}
