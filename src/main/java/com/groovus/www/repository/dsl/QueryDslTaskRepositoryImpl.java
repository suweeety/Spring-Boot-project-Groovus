package com.groovus.www.repository.dsl;


import com.groovus.www.dto.TaskDTO;
import com.groovus.www.entity.*;
import com.groovus.www.repository.MemberRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
public class QueryDslTaskRepositoryImpl extends QuerydslRepositorySupport implements QueryDslTaskRepository{


    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     *
     */
    public QueryDslTaskRepositoryImpl() {super(Task.class);}

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Page<TaskDTO> searchAll(String[] types, String keyword, Pageable pageable, Long pid) {

        QTask task = QTask.task;
        QProject project = QProject.project;

        JPQLQuery<Task> query = from(task);
        query.where(task.project.pid.eq(pid));
        query.where(task.del.isFalse());

        if((types != null && types.length>0)&& keyword !=null){
            //검색 조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types){

                switch (type){

                    case "t" :
                        booleanBuilder.or(task.taskTitle.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(task.taskContent.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(task.taskWriter.contains(keyword));
                        break;
                    case "r":
                        booleanBuilder.or(task.responsibleMember.eq(Long.parseLong(keyword)));
                }
            }//end for

            query.where(booleanBuilder);
        }//end if

        this.getQuerydsl().applyPagination(pageable,query);

        List<Task> taskList = query.fetch();

        List<TaskDTO> taskDTOList = taskList.stream().map(tmpTask->{

             Optional<Member> memberResult = memberRepository.findByMid(tmpTask.getResponsibleMember());
             Member member = memberResult.get();

            TaskDTO dto = TaskDTO.builder()
                    .tid(tmpTask.getTid().toString())
                    .pid(tmpTask.getProject().getPid().toString())
                    .taskTitle(tmpTask.getTaskTitle())
                    .taskWriter(tmpTask.getTaskWriter())
                    .taskContent(tmpTask.getTaskContent())
                    .responsibleMember(member.getUid())
                    .status(tmpTask.getStatus())
                    .regDate(tmpTask.getRegDate())
                    .modDate(tmpTask.getModDate())
                    .build();
            return dto;

        }).collect(Collectors.toList());

        long totalCount = query.fetchCount();

        return new PageImpl<TaskDTO>(taskDTOList,pageable,totalCount);
    }
}
