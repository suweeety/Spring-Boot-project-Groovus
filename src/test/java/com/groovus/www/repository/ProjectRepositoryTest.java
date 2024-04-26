package com.groovus.www.repository;

import com.groovus.www.dto.RegisterProjectDTO;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Project;
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

import java.util.Optional;

@SpringBootTest
@Log4j2
public class ProjectRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void insertProjectMember(){

      Optional<Member> member = memberRepository.findByUid("jeongeun");

      Member testMember = member.get();

    Project project = Project.builder()
              .projectName("테스트프로젝트")
              .projectPassword("1111")
              .projectDescription("테스트설명")
              .adminUid("jeongeun")
              .build();

      project.addMember(testMember);

      projectRepository.save(project);


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


        Page<RegisterProjectDTO> result = projectRepository.getProjectListWithMid(testMember.getMid(),pageable);


        result.getContent().forEach(project1->{

            log.info("==========================결과=========================");
            log.info(project1);


        });


    }

    @Test
    @Transactional
    @Commit
    public void deleteProject(){

        projectRepository.deleteProject(5L);


    }

}
