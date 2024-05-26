package com.groovus.www.controller;

import com.groovus.www.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    //회원 관련 컨트롤러

    private final MemberService memberService;


    @GetMapping("/socialmodify")
    public void goSocialLogin(){

    }


    @PostMapping("/socialLogin")
    public String socialLogin(String mid, String newPw){


        if(memberService.chagePwForSocialLogin(mid,newPw)){

            return "member/login";

        }else{

            return "error/error500";

        }

    }

    @GetMapping("/join")
    public String signUp(){

        return "member/join";
    }


}
