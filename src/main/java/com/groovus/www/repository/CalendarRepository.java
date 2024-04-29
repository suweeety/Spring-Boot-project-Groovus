package com.groovus.www.repository;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.entity.Calendar;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.parameters.P;

import java.sql.PreparedStatement;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    @EntityGraph(attributePaths = {"imageSet"})
    @Query("SELECT c FROM Calendar c WHERE c.cal_id =:cal_id")
    Optional<Calendar> findByIdWithImages(Long cal_id);

}
