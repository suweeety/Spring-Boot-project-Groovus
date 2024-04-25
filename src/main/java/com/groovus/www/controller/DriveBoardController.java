package com.groovus.www.controller;

import com.groovus.www.dto.DriveBoardDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.entity.QDriveBoard;
import com.groovus.www.service.DriveBoardService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

      @PostMapping("/register")
      public String register(DriveBoardDTO driveBoardDTO, RedirectAttributes redirectAttributes){

        Long bno = driveBoardService.register(driveBoardDTO);

        redirectAttributes.addFlashAttribute("msg", bno );

        return "redirect:/drive/drive";
      } // register





}
