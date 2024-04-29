package com.groovus.www.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {


    @GetMapping("/error/error500")
    public void error500(){

    }



}
