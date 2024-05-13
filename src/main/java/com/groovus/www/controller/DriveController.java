package com.groovus.www.controller;

import com.mysema.commons.lang.URLEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.parameters.P;
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

    @GetMapping("/register/{pid}/{projectName}")
    public String register(@PathVariable("pid") String pid , @PathVariable("projectName")String projectName , Model model){

        model.addAttribute("pid",pid);
        model.addAttribute("projectName",projectName);

        return "drive/register";
    }

    @PostMapping("/register/{pid}/{projectName}")
    public String register(DriveDTO driveDTO, RedirectAttributes redirectAttributes,@PathVariable("pid") String pid , @PathVariable("projectName")String projectName){

        log.info("driveDTO: " + driveDTO);

        Long bno = driveService.register(driveDTO);

        redirectAttributes.addFlashAttribute("msg", bno);
        redirectAttributes.addFlashAttribute("pid",pid);
        redirectAttributes.addFlashAttribute("projectName",projectName);
        String newUrl = URLEncoder.encodeURL("/drive/drive/"+pid+"/"+projectName);
        return "redirect:"+newUrl;
    }

    @GetMapping("/drive/{pid}/{projectName}")
    public String list(PageRequestDTO pageRequestDTO, Model model ,@PathVariable("pid") String pid , @PathVariable("projectName")String projectName){
        //리스트로 이동

        log.info("pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("pid",pid);
        model.addAttribute("projectName",projectName);
        model.addAttribute("result", driveService.getList(pageRequestDTO));

        return "drive/drive";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model, String pid ,String projectName ){

        log.info("bno: " + bno);

        DriveDTO driveDTO = driveService.getDrive(bno);

        model.addAttribute("dto", driveDTO);
        model.addAttribute("pid",pid);
        model.addAttribute("projectName",projectName);

    }

    @PostMapping("/modify/{pid}/{projectName}")
    public String modify(DriveDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes,@PathVariable("pid") String pid , @PathVariable("projectName")String projectName){

        driveService.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("bno", dto.getBno());
        redirectAttributes.addFlashAttribute("pid",pid);
        redirectAttributes.addFlashAttribute("projectName",projectName);

        String newUrl = URLEncoder.encodeURL("/drive/read?pid="+pid+"&projectName="+projectName);

        return "redirect:"+newUrl;
    }

//    @PostMapping("/bin/{bno}")
//    }






}
