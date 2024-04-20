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




}
