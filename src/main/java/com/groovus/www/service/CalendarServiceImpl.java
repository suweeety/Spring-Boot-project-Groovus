package com.groovus.www.service;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.entity.Calendar;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Project;
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

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CalendarServiceImpl implements CalendarService{

    private final ModelMapper modelMapper;

    private final CalendarRepository calendarRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long register(CalendarDTO calendarDTO) { // 일정 등록

        log.info("DTO--------------------------------");
        log.info(calendarDTO);

        Optional<Member> result = memberRepository.findByUid(calendarDTO.getCreate_user_id());

        Member member = result.get();

       Calendar calendar = dtoToEntity(calendarDTO,member);

       log.info(calendar);

       calendarRepository.save(calendar);

       return calendar.getCal_id();
    }

    @Override
    public List<CalendarDTO> getList(Long pid) { // 전체일정 가져오는 용

        List<Calendar> result = calendarRepository.getCalendarsByProjectOrderByCal_id(pid);
        if(!result.isEmpty()){
            return result.stream().map(calendar -> entityToDto(calendar)).collect(Collectors.toList());
        }else{
            return null;
        }
    }

    @Override
    public CalendarDTO readOne(Long pid, Long cal_id) { // 하나의 일정 가져오는 용

        Optional<Calendar> result = calendarRepository.findCalendarByCal_idAndProject(pid, cal_id);

        log.info("=======================================================");
        log.info(result);
        log.info(pid);
        log.info(cal_id);
        log.info("=======================================================");

        return result.isPresent() ? entityToDto(result.get()) : null;

    }


    @Override
    public void modify(CalendarDTO calendarDTO, Member createMember) {

//        Optional<Calendar> result = calendarRepository.findById(calendarDTO.getCal_id());

//        Calendar calendar = result.orElseThrow();

        Calendar calendar = dtoToEntity(calendarDTO, createMember);

        calendarRepository.save(calendar);
    }

    @Override
    public void remove(Long cal_id) {

        calendarRepository.deleteById(cal_id);

    }




}
