package com.groovus.www.repository;

import com.groovus.www.entity.Task;
import com.groovus.www.entity.TaskReply;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskReplyRepository extends JpaRepository<TaskReply,Long> {

        //업무 번호로 댓글 리스트를 가져옴

    @EntityGraph(attributePaths = "task")
    @Query("select r from TaskReply  r where r.task=:task")
    List<TaskReply> getTaskReplyByTid(Task task);


}
