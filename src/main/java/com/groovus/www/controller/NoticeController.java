package com.groovus.www.controller;

import com.groovus.www.dto.MemberDTO;
import com.groovus.www.dto.ProjectPageRequestDTO;
import com.groovus.www.dto.ProjectPageResponseDTO;
import com.groovus.www.dto.ResponseDTO;
import com.groovus.www.dto.notice.NoticeRequestDTO;
import com.groovus.www.dto.notice.NoticeResponseDTO;
import com.groovus.www.service.notice.NoticeServiceImpl;
import groovy.util.logging.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeServiceImpl noticeService;

    //워크스페이스에 따라 구분
    //공지사항 생성
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<ResponseDTO<NoticeResponseDTO>> createNotice(NoticeRequestDTO noticeRequestDTO,
                                                                       @AuthenticationPrincipal MemberDTO memberDTO) {
        return noticeService.createNotice(noticeRequestDTO, memberDTO);
    }

    //공지사항 조회
    @GetMapping("/noticeList/{pid}/{projectName}")
    public String noticeList(ProjectPageRequestDTO pageRequestDTO, Model model , @PathVariable("pid")String pid , @PathVariable("projectName")String projectName) {

        ProjectPageResponseDTO<NoticeResponseDTO> noticeList = noticeService.noticeList(pageRequestDTO);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("pageRequestDTO", pageRequestDTO);
        model.addAttribute("pid",pid);
        model.addAttribute("projectName",projectName);

        return "notice/noticeList";
    }

    @PostMapping("/view")
    @ResponseBody
    public ResponseEntity<ResponseDTO<NoticeResponseDTO>> readNotice(Long nid) {

        return noticeService.readNotice(nid);
    }

    //공지사항 수정
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<ResponseDTO<NoticeResponseDTO>> updateNotice(NoticeRequestDTO noticeRequestDTO, Long nid) {
        return noticeService.updateNotice(noticeRequestDTO, nid);
    }


    //공지사항 삭제
    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<ResponseDTO<String>> deleteNotice(Long nid) {
        return noticeService.deleteNotice(nid);
    }

}