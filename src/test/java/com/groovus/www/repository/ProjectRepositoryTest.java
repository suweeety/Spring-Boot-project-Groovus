package com.groovus.www.repository;

import com.groovus.www.dto.ProjectPageRequestDTO;
import com.groovus.www.dto.ProjectPageResponseDTO;
import com.groovus.www.dto.RegisterProjectDTO;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Project;
import com.groovus.www.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ProjectRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    @Test
    @Transactional
    @Commit
    public void insertProjectMember(){

      Optional<Member> member = memberRepository.findByUid("jeongeun");

      Member testMember = member.get();


        IntStream.rangeClosed(1,20).forEach(value -> {
            Project project = Project.builder()
                    .projectName("테스트프로젝트"+value)
                    .projectPassword("1111")
                    .projectDescription("테스트설명")
                    .adminUid("jeongeun")
                    .build();

            project.addMember(testMember);
            projectRepository.save(project);

                }
        );


    }

    @Test
    public void insertMember(){

        Optional<Member> member = memberRepository.findByUid("jeongeun2");
        Member testMember = member.get();

        Optional<Project> project = projectRepository.findByIdWithMember(5L);

        Project testProject = project.get();

        testProject.addMember(testMember);

        projectRepository.save(testProject);

    }

    @Test
    @Transactional
    public void getProjects(){

        Optional<Member> member = memberRepository.findByUid("jeongeun2");
        Member testMember = member.get();

        Pageable pageable =PageRequest.of(0,10);


        List<RegisterProjectDTO> result = projectRepository.getProjectListWithMid(testMember.getMid());




    }

    @Test
    @Transactional
    @Commit
    public void deleteProject(){

        projectRepository.deleteProject(5L);


    }

    @Test
    @Transactional
    public void testPaging(){

        Optional<Member> member = memberRepository.findByUid("jeongeun");
        Member testMember = member.get();

        ProjectPageRequestDTO pageRequestDTO = ProjectPageRequestDTO.builder()
                .page(2)
                .size(10)
                .build();
        ProjectPageResponseDTO<RegisterProjectDTO> responseDTO =projectService.getProjectListWithPaging(pageRequestDTO,testMember.getMid());

        log.info(responseDTO);



    }
    @Test
    @Transactional
    public void selectMemberInfo(){

       Optional<Project> project = projectRepository.findByIdWithMember(11L);

       Project result = project.get();

       log.info("=========================================");
       log.info( result.getProjectMember());
       log.info("=========================================");
    }


}
