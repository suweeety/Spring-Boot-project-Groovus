package com.groovus.www.service;

import com.groovus.www.dto.*;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Project;
import com.groovus.www.repository.MemberRepository;
import com.groovus.www.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public List<MemberInfoDTO> getProjectMembers(String pid) {

         Optional<Project> result =  projectRepository.findByIdWithMember(Long.parseLong(pid));

         Project project = result.get();

         List<MemberInfoDTO>memberList = project.getProjectMember().stream().map(member -> {

             MemberInfoDTO dto = MemberInfoDTO.builder()
                     .uid(member.getUid())
                     .mid(member.getMid()+"")
                     .del(member.isDel())
                     .uname(member.getUname())
                     .email(member.getEmail())
                     .build();
             return dto;

         }).collect(Collectors.toList());
        return memberList;
    }

    @Override
    public RegisterProjectDTO getProjectDTO(Long pid) {

        Optional<Project> result = projectRepository.findById(pid);
        if(!result.isEmpty()){
            Project project = result.get();
            RegisterProjectDTO projectDTO = RegisterProjectDTO.builder()
                    .pid(project.getPid().toString())
                    .projectName(project.getProjectName())
                    .regDate(project.getRegDate())
                    .projectPassword(project.getProjectPassword())
                    .adminUid(project.getAdminUid())
                    .projectDescription(project.getProjectDescription())
                    .projectMember(project.getProjectMember().stream().map(member -> {
                        return member.getUid();
                    }).collect(Collectors.toList()))
                    .memberCount(project.getProjectMember().size())
                    .build();

            return projectDTO;
        }else{
            return null;
        }
    }

    @Override
    public boolean changeDes(Long pid, String des) {

        Optional<Project> result = projectRepository.findById(pid);

        if(result.isPresent()){
            Project project =  result.get();
            project.changeProjectDescription(des);
            projectRepository.save(project);
            return true;
        }else{

            return false;
        }
    }

    @Override
    public boolean changePw(Long pid, String pw) {
        Optional<Project> result = projectRepository.findById(pid);

        if(result.isPresent()){
            Project project =  result.get();
            project.changePassword(pw);
            projectRepository.save(project);
            return true;
        }else{

            return false;
        }
    }

    @Override
    public boolean addMember(Long pid, List<String> newMembers) {

        Optional<Project> result = projectRepository.findByIdWithMember(pid);
        if(result.isPresent()){
            Project project = result.get();

            for(String member : newMembers){

                Optional<Member> tmpResult = memberRepository.findByUid(member);
                if(tmpResult.isPresent()){
                    Member tmpMember = tmpResult.get();
                    if(project.getProjectMember().contains(tmpMember)){

                    }else {

                        project.addMember(tmpMember);
                    }
                }
            }

            projectRepository.save(project);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteMember(Long pid, String deleteMember) {

        Optional<Project> result = projectRepository.findByIdWithMember(pid);

        if(result.isPresent()){
            Project project =  result.get();

            Optional<Member> memberResult = memberRepository.findByUid(deleteMember);
            if(memberResult.isPresent()){
                Member member = memberResult.get();
                project.deleteMember(member);
                projectRepository.save(project);
                return true;
            }else{
                return false;
            }

        }else{

            return false;
        }

    }


}
