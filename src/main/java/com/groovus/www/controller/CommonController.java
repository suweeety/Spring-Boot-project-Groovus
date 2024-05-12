package com.groovus.www.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommonController {

    //공통 컨트롤러입니다.
    //페이지 분기시에 사용합니다.!
    //메인화면에서 이동할때나....

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

    @GetMapping("/calendar/schedule")
    public String goScheduleManagement(){
        //일정관리로 이동

        return "calendar/schedule";
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
