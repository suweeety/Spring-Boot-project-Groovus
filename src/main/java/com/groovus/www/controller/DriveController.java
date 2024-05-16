package com.groovus.www.controller;

import org.springframework.core.io.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public String modify( PageRequestDTO pageRequestDTO,
                          @Valid DriveDTO driveDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){

        log.info("drive modify post......." + driveDTO);

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );

            redirectAttributes.addAttribute("bno", driveDTO.getBno());

            return "redirect:/drive/modify?"+link;
        }

        driveService.modify(driveDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", driveDTO.getBno());

        return "redirect:/drive/drive";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/remove")
    public String remove(String bno, RedirectAttributes redirectAttributes){

        log.info("================remove bno'==================");
        log.info(bno);
        driveService.remove(Long.parseLong(bno));

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/drive/drive";
    }


    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {

        Resource resource = new FileSystemResource("c:\\upload\\" + fileName);

        if (resource.exists() == false) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String resourceName = resource.getFilename();

        // remove UUID
        String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);

        HttpHeaders headers = new HttpHeaders();
        try {

            String downloadName = null;

            if ( userAgent.contains("Trident")) {
                log.info("IE browser");
                downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", " ");
            }else if(userAgent.contains("Edge")) {
                log.info("Edge browser");
                downloadName =  URLEncoder.encode(resourceOriginalName,"UTF-8");
            }else {
                log.info("Chrome browser");
                downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
            }

            log.info("downloadName: " + downloadName);

            headers.add("Content-Disposition", "attachment; filename=" + downloadName);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }
}
