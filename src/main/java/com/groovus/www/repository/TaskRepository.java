package com.groovus.www.repository;

import com.groovus.www.entity.Task;
import com.groovus.www.repository.dsl.QueryDslTaskRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> , QueryDslTaskRepository {

    //프로젝트 번호로 업무 목록을 가져오는 쿼리
    @EntityGraph(attributePaths = "project")
    @Query("select t from Task t where t.project=:pid")
    List<Task> getTaskByPid(Long pid);

    //pid와 tid로 업무항목을 가져오는 쿼리
    @EntityGraph(attributePaths = "project")
    @Query("select t from Task t where t.project=:pid and t.tid=:tid")
    Optional<Task> getTaskByPidandTid(Long pid, Long tid);

    @EntityGraph(attributePaths = "project")
    @Query("select count(t) from Task t where t.project.pid=:pid and t.del=false")
    Long countTask(Long pid);

    @EntityGraph(attributePaths = "project")
    @Query("select t from Task t where t.tid=:tid")
    Optional<Task> getTaskByTid(Long tid);


}
