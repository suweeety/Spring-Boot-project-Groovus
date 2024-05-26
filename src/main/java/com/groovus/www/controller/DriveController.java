package com.groovus.www.controller;

import com.mysema.commons.lang.URLEncoder;
import org.springframework.core.io.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.UnsupportedEncodingException;
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

    @GetMapping("/drive/{pid}/{projectName}")
    public String list (@PathVariable("pid") String pid, @PathVariable("projectName") String projectName,  PageRequestDTO pageRequestDTO, Model model) {

        PageResponseDTO<DriveDTO> responseDTO = driveService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);
        model.addAttribute("pid",pid);
        model.addAttribute("projectName",projectName);

        return "drive/drive";
    } // list

    @GetMapping("/register/{pid}/{projectName}")
    public String registerGet(@PathVariable("pid") String pid, @PathVariable("projectName") String projectName ,Model model) {
        model.addAttribute("pid",pid);
        model.addAttribute("projectName",projectName);

        return "drive/register";
    }

    @PostMapping("/register/{pid}/{projectName}")
    public String registerPost(@PathVariable("pid") String pid, @PathVariable("projectName") String projectName,@Valid DriveDTO driveDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("has errors...");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
        }

        log.info(driveDTO);

        Long bno = driveService.register(driveDTO);

        redirectAttributes.addFlashAttribute("result", bno);
        redirectAttributes.addFlashAttribute("pid",pid);
        redirectAttributes.addFlashAttribute("projectName",projectName);


        String newUrl = URLEncoder.encodeURL("/drive/drive/"+pid+"/"+projectName);
        return "redirect:"+newUrl;
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model , String pid , String projectName) {

        DriveDTO driveDTO = driveService.readOne(bno);

        log.info(driveDTO);

        model.addAttribute("pid",pid);
        model.addAttribute("projectName",projectName);
        model.addAttribute("dto", driveDTO);

    } // read

    @PostMapping("/modify/{pid}/{projectName}")
    public String modify( PageRequestDTO pageRequestDTO,
                          @Valid DriveDTO driveDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes , @PathVariable("pid") String pid, @PathVariable("projectName") String projectName){

        log.info("drive modify post......." + driveDTO);

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
            redirectAttributes.addAttribute("bno", driveDTO.getBno());
            redirectAttributes.addFlashAttribute("pid",pid);
            redirectAttributes.addFlashAttribute("projectName",projectName);

            String newUrl = URLEncoder.encodeURL("/drive/modify?"+link+"&pid="+pid+"&projectName="+projectName);


            return "redirect:"+newUrl;
        }

        driveService.modify(driveDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", driveDTO.getBno());
        redirectAttributes.addFlashAttribute("pid",pid);
        redirectAttributes.addFlashAttribute("projectName",projectName);

        String newUrl = URLEncoder.encodeURL("/drive/drive/"+pid+"/"+projectName);
        return "redirect:"+newUrl;
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/remove/{pid}/{projectName}")
    public String remove(String bno, RedirectAttributes redirectAttributes,@PathVariable("pid") String pid, @PathVariable("projectName") String projectName){

        log.info("================remove bno'==================");
        log.info(bno);
        driveService.remove(Long.parseLong(bno));

        redirectAttributes.addFlashAttribute("result", "removed");
        redirectAttributes.addFlashAttribute("pid",pid);
        redirectAttributes.addFlashAttribute("projectName",projectName);

        String newUrl = URLEncoder.encodeURL("/drive/drive/"+pid+"/"+projectName);
        return "redirect:"+newUrl;
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
                downloadName = java.net.URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", " ");
            }else if(userAgent.contains("Edge")) {
                log.info("Edge browser");
                downloadName =  java.net.URLEncoder.encode(resourceOriginalName,"UTF-8");
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
