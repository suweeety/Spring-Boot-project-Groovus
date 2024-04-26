package com.groovus.www.service;

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

        Pageable pageable = PageRequest.of(0,10);

        Page<RegisterProjectDTO> result = projectRepository.getProjectListWithMid(mid,pageable);

        List<RegisterProjectDTO> resultList = result.stream().collect(Collectors.toList());

        return resultList;
    }

    @Override
    public boolean validProjectPw(String pid, String pw) {

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


}
