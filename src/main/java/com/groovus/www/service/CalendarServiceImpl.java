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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CalendarServiceImpl implements CalendarService{

    private final CalendarRepository calendarRepository;
    private final MemberRepository memberRepository;

    private  final ProjectRepository projectRepository;

    // 프로젝트, 멤버 정보 필요
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
                    .cal_link_list(calendarRequestDTO.getCal_link_list())
                    .create_user_id(createMember)
                    .build();

        // 프로젝트에 등록
        Optional<Project> proResult = projectRepository.findByIdWithMember(pid);
        if (proResult.isPresent()){

            Project project = proResult.get();
            calendar.setProject(project);
        }

        // 멤버 초대
        for (String cal_members : calendarRequestDTO.getCal_members()) {
            log.info("cal_members: " + cal_members);
            Optional<Member> memberResult = memberRepository.findByUid(cal_members);
            log.info("memberResult: " + memberResult);
            if (memberResult.isPresent()) {
                Member member = memberResult.get();
                log.info("member: " + member);
                calendar.addMember(member);
            }
        }

        log.info("register method: "+calendar);
        calendarRepository.save(calendar);
        log.info("calendar.getCal_id(): "+calendar.getCal_id());

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
    public CalendarDTO readOne(Long pid, Long cal_id) {

        // 하나의 일정 가져오는 용
        Optional<Calendar> result = calendarRepository.findCalendarByCal_idAndProject(pid, cal_id);

        // pid, cal_id로 가져온 calendar 정보(result)
        if(result.isPresent()){
            Calendar calendar = result.get();

            log.info("=====================calendar========================");
            log.info(calendar.getCreate_user_id().getUid());
            log.info(calendar);
            log.info("====================================================");

            // 일정 등록자용
            Optional<Member> memberResult = memberRepository.getMemberByMid(calendar.getCreate_user_id().getMid());

            // 일정 등록자 정보가 있으면
            if(memberResult.isPresent()){
                Member member = memberResult.get();

                // member라는 이름으로 changeUpdateMember 메서드의 update_user_id에 담음
                calendar.changeUpdateMember(member);
                calendarRepository.save(calendar);

                CalendarDTO dto = CalendarDTO.builder()
                        .cal_title(calendar.getCal_title())
                        .cal_content(calendar.getCal_content())
                        .cal_cate(calendar.getCal_cate())
                        .cal_startDate(calendar.getCal_startDate())
                        .cal_endDate(calendar.getCal_endDate())
                        .cal_link_list(calendar.getCal_link_list())
                        .cal_members(calendar.getCal_members().stream().map(member1 -> {
                            String uid = member1.getUid();
                            return uid;
                        }).collect(Collectors.toList()))
                        .update_user_id(calendar.getUpdate_user_id().getUid())
                        .build();
                log.info("=======================================================");
                log.info(dto);
                log.info("=======================================================");

                return dto;

            }else{
                return null;
            }
        }else {
            return null;
        }
    }

    @Override
    public void modify(CalendarRequestDTO calendarRequestDTO, Long pid, Long cal_id) {

        Optional<Calendar> calResult = calendarRepository.findById(cal_id);
        if(calResult.isPresent()){
            Calendar calendar = calResult.get();

            log.info("======cal_id는-=====>");
            log.info(calendar.getCal_id());
            log.info(cal_id);
            log.info("======cal_id끝-=====>");
            
            calendar.change(calendarRequestDTO.getCal_title(), calendarRequestDTO.getCal_content(), calendarRequestDTO.getCal_cate(),
                    calendarRequestDTO.getCal_startDate(), calendarRequestDTO.getCal_endDate(), calendarRequestDTO.getCal_link_list());

            Optional<Member> memberResult = memberRepository.findByUid(calendarRequestDTO.getUpdate_user_id());

            if(memberResult.isPresent()){

                Member member = memberResult.get();

                calendar.setUpdate_user_id(member);

                calendar.addMember(member);

                List<String> members = calendarRequestDTO.getCal_members();

                for(String imember : members){

                    Optional<Member> inviteResult = memberRepository.findByUid(imember);
                    if(inviteResult.isPresent()){
                        Member inviteMember = inviteResult.get();
                        calendar.addMember(inviteMember);
                    }

                }
            }

        }
    }

    @Override
    public boolean remove(Long cal_id) {

        calendarRepository.deleteByCal_id(cal_id);

        return false;

    }

    @Override
    public int countSchedule(Long pid) {

        return calendarRepository.countCalendarsByProject(pid);
    }

    @Override
    public List<CalendarRequestDTO> getInvitedMembers(Long pid) {

        return null;
    }


}
