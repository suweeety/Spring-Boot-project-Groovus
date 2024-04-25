package com.groovus.www.repository;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.entity.Calendar;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.parameters.P;

import java.sql.PreparedStatement;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {


//    public static final String insert = "INSERT INTO tb_calendar VALUES" +
//            "(cal_id, cal_title, cal_content, cal_period, calendarCategories, regDate, modDate)";
//    @Transactional
//    @Query(value = insert, nativeQuery = true)
//    public Long insert(Calendar calendar);
}
