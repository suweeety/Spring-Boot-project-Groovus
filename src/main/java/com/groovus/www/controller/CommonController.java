package com.groovus.www.controller;

import com.groovus.www.dto.CalendarDTO;
import com.groovus.www.dto.MemberDTO;
import com.groovus.www.entity.Calendar;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Project;
import com.groovus.www.repository.CalendarRepository;
import com.groovus.www.repository.ProjectRepository;
import com.groovus.www.service.CalendarService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
public class CommonController {

    //공통 컨트롤러입니다.
    //페이지 분기시에 사용합니다.!
    //메인화면에서 이동할때나....

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private ProjectRepository projectRepository;

    @PreAuthorize("permitAll()")
    @GetMapping("/")
    public String helloGroovus(){

        return "main/main";
    }
    @PreAuthorize("permitAll()")
    @PostMapping ("/")
    public String LoginGroovus(){

        return "main/main";
    }

    @GetMapping("/main/test")
    public String tesPage(){

        return "main/test";
    }

    @GetMapping("/reply/myreply/{pid}/{projectName}")
    public String goMyReply(Model model,@PathVariable("pid") String pid, @PathVariable("projectName") String projectName){
        //내가 쓴 댓글로 이동
        model.addAttribute("pid",pid);
        model.addAttribute("projectName",projectName);
        return "reply/myreply";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/task/taskList")
    public void goTaskList(){
        //업무리스트로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/calendar/schedule/{pid}/{projectName}")
    public String goScheduleManagement(@PathVariable("pid") String pid, @PathVariable("projectName") String projectName ,Model model){
        //일정관리로 이동
        log.info("---------------------------------------");
        log.info("pid****: " + pid);
        List<CalendarDTO> calendarList = calendarService.getList(Long.parseLong(pid));
        log.info(calendarList);
//        CalendarDTO calendarDTO = calendarService.readOne(Long.parseLong(pid), Long.parseLong(cal_id));
//        log.info(calendarDTO);
        log.info("---------------------------------------");

        model.addAttribute("calendarList", calendarList);
        model.addAttribute("pid",pid);
        model.addAttribute("projectName",projectName);

//        model.addAttribute("calendarDTO", calendarDTO);

        return "calendar/schedule";
    }

    @GetMapping("/message/messageList")
    public void goMessageList(){
        //쪽지함 이동

    }

    @GetMapping("/drive/bin/{pid}/{projectName}")
    public String goDriveBin(@PathVariable("pid") String pid , @PathVariable("projectName") String projectName , Model model){
        //드라이브 휴지통

        model.addAttribute("pid",pid);
        model.addAttribute("projectName",projectName);
        return "drive/bin";
    }
    
    @GetMapping("/setting/{pid}/{projectName}")
    public String goSetting(Model model,@PathVariable("pid") String pid, @PathVariable("projectName") String projectName){
        //설정페이지로 이동
        model.addAttribute("pid",pid);
        model.addAttribute("projectName",projectName);
        return "setting";
    }
    
    @GetMapping("/main/about")
    public void  goAbout(){
        //어바웃 페이지 이동
    }

    @GetMapping("/main/function")
    public void  goFunction(){
        //기능소개 페이지로 이동
    }

    @GetMapping("/main/register")
    public void  goRegister(){
        //프로젝트 생성 페이지로 이동
    }

    @GetMapping("/member/login")
    public void  goLogin(@RequestParam(value = "error" ,required = false) String error , Model model){
        //로그인 페이지로 이동

        if( error != null &&error.equals("wrongInfo")){

            model.addAttribute("msg","loginerror");
        }
        if( error != null &&error.equals("existEmail")){
            model.addAttribute("socialMsg","loginerror");
        }
    }

}
