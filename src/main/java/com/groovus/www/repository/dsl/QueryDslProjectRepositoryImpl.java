package com.groovus.www.repository.dsl;

import com.groovus.www.dto.RegisterProjectDTO;
import com.groovus.www.entity.Project;
import com.groovus.www.entity.QMember;
import com.groovus.www.entity.QProject;
import com.querydsl.jpa.JPQLQuery;
import groovy.lang.Tuple;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class QueryDslProjectRepositoryImpl extends QuerydslRepositorySupport implements QueryDslProjectRepository{
    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public QueryDslProjectRepositoryImpl() {
        super(Project.class);
    }
    @Override
    public Page<RegisterProjectDTO> getProjectListWithMid(Long mid, Pageable pageable) {

        QProject project = QProject.project;
        QMember member = QMember.member;

        JPQLQuery<Project> projectJPQLQuery = from(project)
                .join(project.projectMember, member)
                .where(member.mid.eq(mid));

        this.getQuerydsl().applyPagination(pageable, projectJPQLQuery);

        List<Project> projectList = projectJPQLQuery.select(project).fetch();

        List<RegisterProjectDTO> projectDTOList= projectList.stream().map(project1 -> {

            List<String> memberUids = project1.getProjectMember().stream().map(member1 -> {
                return member1.getUid();
            }).collect(Collectors.toList());

            RegisterProjectDTO dto = RegisterProjectDTO.builder()
                    .pid(project1.getPid().toString())
                    .projectName(project1.getProjectName())
                    .projectPassword(project1.getProjectPassword())
                    .projectDescription(project1.getProjectDescription())
                    .adminUid(project1.getAdminUid())
                    .regDate(project1.getRegDate())
                    .memberCount(project1.getProjectMember().size())
                    .projectMember(memberUids)
                    .build();

            return dto;
            }).collect(Collectors.toList());

        long totalCount = projectJPQLQuery.fetchCount();

        return new PageImpl<RegisterProjectDTO>(projectDTOList,pageable,totalCount);
    }
}
