package com.groovus.www.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "project")
public class Task extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid; // 업무번호

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project; //속해있는 프로젝트 pid

    private String taskTitle; //업무제목

    private String taskWriter; //업무 작성자

    private Long responsibleMember; //mid => 담장자

    @Column(length = 300000)
    private String taskContent; //업무 내용

    @Builder.Default
    private boolean del =false ; //삭제여부

    @Builder.Default
    private TaskStatus status = TaskStatus.TODO; // 업무상태 (할일,진행중,완료,보류)

    //업무 제목 바꾸기
    public void changeTitle(String newTitle){
        this.taskTitle = newTitle;
    }

    //업무 내용 바꾸기
    public void changeContent(String newContent){
        this.taskContent = newContent;
    }

    //업무 담당자 바꾸기
    public void changeResponsibleMember(Long mid){
        this.responsibleMember = mid;
    }

    //삭제여부 바꾸기
    public void  changeDel(boolean del){
        this.del=del;
    }

    //프로젝트 세팅
    public void setProject(Project project){
        this.project = project;
    }

    //업무 상태 변경
    public void changeStatus(TaskStatus status){
        this.status=status;
    }
}
