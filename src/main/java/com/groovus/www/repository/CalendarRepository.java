package com.groovus.www.repository;

import com.groovus.www.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
