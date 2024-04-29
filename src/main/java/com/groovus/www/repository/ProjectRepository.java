package com.groovus.www.repository;

import com.groovus.www.entity.Member;
import com.groovus.www.entity.MemberRole;
import com.groovus.www.entity.Project;
import com.groovus.www.repository.dsl.QueryDslProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> , QueryDslProjectRepository{

    @EntityGraph(attributePaths = "projectMember")
    @Query("select p from Project p where p.pid=:pid")
    Optional<Project> findByIdWithMember(Long pid);


    @Query("select p from Project p join fetch p.projectMember pm where pm.mid = :mid")
    Page<Project> getProjectList(@Param("mid") Long mid,Pageable pageable);


   @Query("delete from Project p where p.pid=:pid")
   @EntityGraph(attributePaths = "projectMember")
   @Modifying
    int deleteProject(Long pid);

}
