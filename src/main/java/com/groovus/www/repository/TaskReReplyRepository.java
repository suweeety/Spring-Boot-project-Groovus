package com.groovus.www.repository;

import com.groovus.www.entity.TaskReReply;
import com.groovus.www.entity.TaskReply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskReReplyRepository extends JpaRepository<TaskReReply,Long> {


    //댓글번호로 대댓글 리스트를 가져옴
    @EntityGraph(attributePaths = "taskReply")
    @Query("select r from TaskReReply r where r.taskReply=:taskReply")
    List<TaskReReply> getTaskReRepliesByRid(TaskReply taskReply);

    //작성자 아이디로 대댓글 리스트 가져옴
    @EntityGraph(attributePaths = "taskReply")
    @Query("select r from  TaskReReply r where r.uid=:uid and r.taskReply.task.project.pid=:pid")
    List<TaskReReply> getTaskReRepliesByUid(String uid, Long pid);

}
