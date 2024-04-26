package com.groovus.www.service;

import com.groovus.www.dto.CalendarCategoryDTO;
import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.entity.Calendar;
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
public class CalendarServiceImpl implements CalendarService{

    private final ModelMapper modelMapper;

    private final CalendarRepository calendarRepository;

    @Override
    public Long register(CalendarDTO calendarDTO) {

        Calendar calendar = modelMapper.map(calendarDTO, Calendar.class);

        Long cal_id = calendarRepository.save(calendar).getCal_id();

        log.info("calendarDTO.getCal_id() 값 확인: " + calendarDTO.getCal_id());
        log.info("cal_id 값 확인: " + cal_id);

        return cal_id;
    }

    @Override
    public CalendarDTO readOne(Long cal_id) {

        Optional<Calendar> result = calendarRepository.findById(cal_id);

        Calendar calendar = result.orElseThrow();

        CalendarDTO calendarDTO = modelMapper.map(calendar, CalendarDTO.class);

        return calendarDTO;
    }

    @Override
    public void modify(CalendarDTO calendarDTO) {

//        Optional<Calendar> result = calendarRepository.findById(calendarDTO.getCal_id());
//
//        Calendar calendar = result.orElseThrow();
//
//        calendar.change(calendarDTO.getCal_title(), calendarDTO.getCal_content(), calendarDTO.getCal_Categories(), calendarDTO.getCal_endDate(), calendarDTO.getCal_startDate());
//
//        calendarRepository.save(calendar);
    }

    @Override
    public void remove(Long cal_id) {

        calendarRepository.deleteById(cal_id);

    }

}
