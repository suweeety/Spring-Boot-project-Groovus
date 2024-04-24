package com.groovus.www.controller;

import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.service.DriveBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/drive")
@Log4j2
@RequiredArgsConstructor
public class DriveBoardController {

    private final DriveBoardService driveBoardService;

      @GetMapping("/")
        public String index(){

          return "redirect:/drive/drive";
      }

      @GetMapping("/drive")
        public void drive(PageRequestDTO pageRequestDTO, Model model){

          log.info("list...." + pageRequestDTO);

          model.addAttribute("result", driveBoardService.getList(pageRequestDTO));
      } // drive

      @GetMapping("/register")
      public void register(){

      }




}
