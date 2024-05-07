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

    @Override
    public List<CalendarDTO> getList(Long pid) {

        List<Calendar> result = calendarRepository.getCalendarsByProjectOrderByCal_id(pid);
        if(!result.isEmpty()){
            return result.stream().map(calendar -> entityToDto(calendar)).collect(Collectors.toList());
        }else{
            return null;
        }
    }

    @Override
    public CalendarDTO readOne(Long cal_id, Long pid) {
        try {
            if (cal_id == null) {
                throw new IllegalArgumentException("cal_id cannot be null");
            }

            log.info("cal_id in CalendarServiceImpl: " + cal_id);
            Optional<Calendar> result = calendarRepository.findByCal_id(cal_id, pid);

            return result.isPresent() ? entityToDto(result.get()) : null;
        } catch (NumberFormatException e) {
            // cal_id가 숫자로 변환할 수 없는 경우 처리
            log.error("Error parsing cal_id: " + e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            // cal_id가 null인 경우 처리
            log.error("cal_id is null: " + e.getMessage());
            return null;
        }
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
