package com.groovus.www.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.groovus.www.dto.DriveDTO;
import com.groovus.www.dto.PageRequestDTO;
import com.groovus.www.dto.PageResponseDTO;
import com.groovus.www.service.DriveService;

@Controller
@RequestMapping("/drive")
@Log4j2
@RequiredArgsConstructor
public class DriveController {

    private final DriveService driveService;

    @GetMapping("/drive")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        PageResponseDTO<DriveDTO> responseDTO = driveService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);

    } // list

    @GetMapping("/register")
    public void registerGet() {

    }

    @PostMapping("/register")
    public String registerPost(@Valid DriveDTO driveDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("has errors...");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/drive/register";
        }

        log.info(driveDTO);

        Long bno = driveService.register(driveDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/drive/drive";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {

        DriveDTO driveDTO = driveService.readOne(bno);

        log.info(driveDTO);

        model.addAttribute("dto", driveDTO);

    } // read

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO, @Valid DriveDTO driveDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("has errors......");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("bno", driveDTO.getBno());

            return "redirect:/drive/modify?" + link;
        }

        driveService.modify(driveDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", driveDTO.getBno());

        return "redirect:/drive/read";
    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes){
        driveService.remove(bno);

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/drive/drive";
    }

}
