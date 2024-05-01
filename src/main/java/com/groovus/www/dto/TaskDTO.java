package com.groovus.www.dto;

import com.groovus.www.entity.TaskStatus;
import com.groovus.www.service.TaskServiceImpl;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Data
@Log4j2
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskDTO {

    @Builder.Default
    private Long taskNo=1L; // 프로젝트에 존재하는 업무 번호 (tid와는 다름)-> 각 프로젝트마다 순차적인 번호 생성 위해

    private String tid; //프로젝트 업무의 실제 업무 번호 (데이터베이스에서 판단하기위한 번호)

    private String pid; //프로젝트번호

    private String taskTitle; //업무제목

    private String taskWriter; //업무작성자

    private String responsibleMember; //업무 담당자

    private String taskContent; // 업무내용

    private LocalDateTime regDate;// 등록일

    private LocalDateTime modDate;//수정일

    @Builder.Default
    private TaskStatus status = TaskStatus.TODO;
}
