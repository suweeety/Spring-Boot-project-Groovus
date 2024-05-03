package com.groovus.www.service;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.entity.Calendar;
import com.groovus.www.entity.Member;
import com.groovus.www.repository.CalendarRepository;
import com.groovus.www.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CalendarServiceImpl implements CalendarService{

    private final ModelMapper modelMapper;

    private final CalendarRepository calendarRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long register(CalendarDTO calendarDTO) {

        log.info("DTO--------------------------------");
        log.info(calendarDTO);

        Optional<Member> result = memberRepository.findByUid(calendarDTO.getCreate_user_id());

        Member member = result.get();

       Calendar calendar = dtoToEntity(calendarDTO,member);

       log.info(calendar);

       calendarRepository.save(calendar);

       return calendar.getCal_id();
    }

//    @Override
//    public PageResultDTO<CalendarDTO, Calendar> getList(PageRequestDTO requestDTO) {
//
//        Pageable pageable = requestDTO.getPageable(Sort.by("cal_id"));
//
//        Page<Calendar> result = calendarRepository.findAll(pageable);
//
//        Function<Calendar, CalendarDTO> fn = (entity -> entityToDto(entity));
//
//        return new PageResultDTO<>(result, fn);
//    }


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
