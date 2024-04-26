package com.groovus.www.service;

import com.groovus.www.dto.CalendarCategoryDTO;
import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.entity.Calendar;
import com.groovus.www.entity.CalendarCategory;
import com.groovus.www.repository.CalendarCategoryRepository;
import com.groovus.www.repository.CalendarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CalendarCategoryServiceImpl implements CalendarCategoryService {

    private final ModelMapper modelMapper;

    private final CalendarCategoryRepository calendarCategoryRepository;

    @Override
    public Long register(CalendarCategoryDTO calendarCategoryDTO) {

        CalendarCategory calendarCategory = modelMapper.map(calendarCategoryDTO, CalendarCategory.class);

        Long calCate_id = calendarCategoryRepository.save(calendarCategory).getCalCate_id();

        return calCate_id;
    }

    @Override
    public CalendarCategoryDTO readOne(Long calCate_id) {

        Optional<CalendarCategory> result = calendarCategoryRepository.findById(calCate_id);

        CalendarCategory calendarCategory = result.orElseThrow();

        CalendarCategoryDTO calendarCategoryDTO = modelMapper.map(calendarCategory, CalendarCategoryDTO.class);

        return calendarCategoryDTO;
    }

    @Override
    public void modify(CalendarCategoryDTO calendarCategoryDTO) {

//        Optional<CalendarCategory> result = calendarCategoryRepository.findById(calendarCategoryDTO.getCalCate_id());
//
//        CalendarCategory calendarCategory = result.orElseThrow();
//
//        calendarCategory.change(calendarCategoryDTO.getCal_category());
//
//        calendarCategoryRepository.save(calendarCategory);
    }

    @Override
    public void remove(Long cal_id) {

        calendarCategoryRepository.deleteById(cal_id);

    }
}
