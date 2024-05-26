package com.groovus.www.repository.dsl;

import com.groovus.www.dto.RegisterProjectDTO;
import com.groovus.www.entity.Project;
import com.groovus.www.entity.QMember;
import com.groovus.www.entity.QProject;
import com.querydsl.core.BooleanBuilder;
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
     *
     */
    public QueryDslProjectRepositoryImpl() {
        super(Project.class);
    }
    @Override
    public List<RegisterProjectDTO> getProjectListWithMid(Long mid) {

        QProject project = QProject.project;
        QMember member = QMember.member;

        JPQLQuery<Project> projectJPQLQuery = from(project)
                .join(project.projectMember, member)
                .where(member.mid.eq(mid));

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

        return projectDTOList;
    }
    
    @Override
    public Page<RegisterProjectDTO> searchAll(String[] types, String keyword, Pageable pageable ,Long mid) {

        QProject project = QProject.project;
        QMember member = QMember.member;

        JPQLQuery<Project> query = from(project)
                .join(project.projectMember, member)
                .where(member.mid.eq(mid));

        if((types != null && types.length>0)&& keyword !=null){
            //검색 조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types){

                switch (type){

                    case "t" :
                        booleanBuilder.or(project.projectName.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(project.projectDescription.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(project.adminUid.contains(keyword));
                        break;
                  }
            }//end for

            query.where(booleanBuilder);
        }//end if
        query.where(project.pid.gt(0L));

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);

        List<Project> projectList = query.fetch();

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

        long totalCount = query.fetchCount();

        return new PageImpl<RegisterProjectDTO>(projectDTOList,pageable,totalCount);

    }
}
