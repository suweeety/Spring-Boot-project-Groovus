package com.groovus.www.repository;

import com.groovus.www.entity.Task;
import com.groovus.www.entity.TaskReply;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskReplyRepository extends JpaRepository<TaskReply,Long> {

    //업무 번호로 댓글 리스트를 가져옴
    @EntityGraph(attributePaths = "task")
    @Query("select r from TaskReply  r where r.task=:task")
    List<TaskReply> getTaskReplyByTid(Task task);

    //댓글번호로 댓글 가져옴
    @EntityGraph(attributePaths = "task")
    Optional<TaskReply> getTaskReplyByRid(Long rid);

    //작성자 아이디로 댓글 리스트를 가져옴
    @EntityGraph(attributePaths = "task")
    @Query("select r from TaskReply r where r.uid=:uid and r.task.project.pid=:pid")
    List<TaskReply> getTaskReplyListByUid(String uid , Long pid);
}
