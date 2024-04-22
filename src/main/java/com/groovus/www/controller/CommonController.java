package com.groovus.www.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    //공통 컨트롤러입니다.
    //페이지 분기시에 사용합니다.!
    //메인화면에서 이동할때나....


    @GetMapping("/")
    public String helloGroovus(){

        return "/main/test";
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

}
