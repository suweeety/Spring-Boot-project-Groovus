package com.groovus.www.repository;

import com.groovus.www.entity.CalendarAttach;
import com.groovus.www.entity.CalendarCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarCategoryRepository extends JpaRepository<CalendarCategory, Long> {
}
