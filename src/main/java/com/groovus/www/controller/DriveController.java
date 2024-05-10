package com.groovus.www.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.groovus.www.dto.DriveDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.service.DriveService;

@Controller
@RequestMapping("/drive")
@Log4j2
@RequiredArgsConstructor
public class DriveController {

    private final DriveService driveService ;;

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(DriveDTO driveDTO, RedirectAttributes redirectAttributes){

        log.info("driveDTO: " + driveDTO);

        Long bno = driveService.register(driveDTO);

        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/drive/drive";
    }

    @GetMapping("/drive/{pid}/{projectName}")
    public void list(PageRequestDTO pageRequestDTO, Model model ,@PathVariable("pid") String pid , @PathVariable("projectName")String projectName){

        log.info("pageRequestDTO: " + pageRequestDTO);


        model.addAttribute("result", driveService.getList(pageRequestDTO));

    }

    @GetMapping({"/read", "/modify"})
    public void read(long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model ){

        log.info("bno: " + bno);

        DriveDTO driveDTO = driveService.getDrive(bno);

        model.addAttribute("dto", driveDTO);

    }

    @PostMapping("/modify")
    public String modify(DriveDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){

        driveService.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("bno", dto.getBno());

        return "redirect:/drive/read";
    }

//    @PostMapping("/bin/{bno}")
//    }






}
