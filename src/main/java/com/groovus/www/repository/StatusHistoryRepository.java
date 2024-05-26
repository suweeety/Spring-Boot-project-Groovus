package com.groovus.www.repository;

import com.groovus.www.entity.StatusHistory;
import com.groovus.www.entity.Task;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StatusHistoryRepository extends JpaRepository<StatusHistory,Long> {

    @EntityGraph(attributePaths = "tid")
    @Query("select h from StatusHistory h where h.tid=:tid order by h.hid asc")
    List<StatusHistory> getStatusHistoryBytid(Task tid);

}
