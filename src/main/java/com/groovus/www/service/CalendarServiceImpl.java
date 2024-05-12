package com.groovus.www.service;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.dto.CalendarRequestDTO;
import com.groovus.www.entity.Calendar;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Project;
import com.groovus.www.repository.CalendarRepository;
import com.groovus.www.repository.MemberRepository;
import com.groovus.www.repository.ProjectRepository;
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

    private  final ProjectRepository projectRepository;

    @Override
    public Long register(CalendarRequestDTO calendarRequestDTO , Long pid) { // 일정 등록

        log.info("DTO--------------------------------");
        log.info(calendarRequestDTO);

        Optional<Member> result = memberRepository.findByUid(calendarRequestDTO.getCreate_user_id());

        Member createMember = result.get();

            Calendar calendar = Calendar.builder()
                    .cal_title(calendarRequestDTO.getCal_title())
                    .cal_content(calendarRequestDTO.getCal_content())
                    .cal_cate(calendarRequestDTO.getCal_cate())
                    .cal_startDate(calendarRequestDTO.getCal_startDate())
                    .cal_endDate(calendarRequestDTO.getCal_endDate())
                    .create_user_id(createMember)
                    .build();

            Optional<Project> proResult = projectRepository.findByIdWithMember(pid);
            if (proResult.isPresent()){

                Project project = proResult.get();
                calendar.setProject(project);
            }
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
    public void modify(CalendarRequestDTO calendarRequestDTO, Long pid, Long cal_id) {
        //CalendarRequestDTO: String 타입으로 이루어짐

        Optional<Calendar> calResult = calendarRepository.findById(cal_id);
        if(calResult.isPresent()){
            Calendar calendar = calResult.get();

            calendar.change(calendarRequestDTO.getCal_title(), calendarRequestDTO.getCal_content(), calendarRequestDTO.getCal_cate(), calendarRequestDTO.getCal_startDate(), calendarRequestDTO.getCal_endDate());

          Optional<Member> memberResult = memberRepository.findByUid(calendarRequestDTO.getUpdate_user_id());
          if(memberResult.isPresent()){
              Member member = memberResult.get();

              calendar.setUpdate_user_id(member);

              calendarRepository.save(calendar);
          }
        }
    }

    @Override
    public void remove(Long cal_id) {

        calendarRepository.deleteById(cal_id);

    }




}
