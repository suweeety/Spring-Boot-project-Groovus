package com.groovus.www.controller;

import com.groovus.www.dto.MemberDTO;
import com.groovus.www.entity.Member;
import com.groovus.www.repository.CalendarRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@Log4j2
public class CommonController {

    //공통 컨트롤러입니다.
    //페이지 분기시에 사용합니다.!
    //메인화면에서 이동할때나....
    @Autowired
    private CalendarRepository calendarRepository;

    @PreAuthorize("permitAll()")
    @GetMapping("/")
    public String helloGroovus(){

        return "/main/main";
    }

    @PostMapping ("/")
    public String LoginGroovus(){

        return "/main/main";
    }

    @GetMapping("/main/test")
    public String tesPage(){

        return "/main/test";
    }

    @GetMapping("/reply/myreply")
    public void goMyReply(){
        //내가 쓴 댓글로 이동

    }


    @GetMapping("/task/taskList")
    public void goTaskList(){
        //업무리스트로 이동
    }

    @GetMapping("/calendar/schedule")
    public String goScheduleManagement(){
        //일정관리로 이동

        return "/calendar/schedule";
    }

    @GetMapping("/message/messageList")
    public void goMessageList(){
        //쪽지함 이동

    }

    @GetMapping("/drive/drive")
    public void goDrive(){
        //드라이브 이동

    }
    @GetMapping("/drive/bin")
    public void goDriveBin(){
        //드라이브 휴지통

    }
    @GetMapping("/conference/webConference")
    public void goConference(){
        //화상회의로 이동

    }
    
    @GetMapping("/setting")
    public void goSetting(){
        //설정페이지로 이동
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
