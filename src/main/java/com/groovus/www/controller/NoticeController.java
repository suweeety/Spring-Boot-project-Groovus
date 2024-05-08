package com.groovus.www.controller;

import com.groovus.www.dto.MemberDTO;
import com.groovus.www.dto.PagingRequestDTO;
import com.groovus.www.dto.ResponseDTO;
import com.groovus.www.dto.notice.NoticeRequestDTO;
import com.groovus.www.dto.notice.NoticeResponseDTO;
import com.groovus.www.service.NoticeService;
import groovy.util.logging.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    //워크스페이스에 따라 구분
    //공지사항 생성
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<ResponseDTO<NoticeResponseDTO>> createNotice(NoticeRequestDTO noticeRequestDTO,
                                                                       @AuthenticationPrincipal MemberDTO memberDTO) {

        return noticeService.createNotice(noticeRequestDTO, memberDTO);
    }

    //공지사항 조회
    @GetMapping("/noticeList")
    public String noticeList(PagingRequestDTO pagingRequestDTO, Model model) {

        List<NoticeResponseDTO> noticeList = noticeService.noticeList(pagingRequestDTO);
        model.addAttribute("noticeList", noticeList);
        return "notice/noticeList";
    }

    //공지사항 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateNotice(NoticeRequestDTO noticeRequestDTO, @PathVariable Long nid) {
        return noticeService.updateNotice(noticeRequestDTO, nid);
    }

    //공지사항 삭제
    @DeleteMapping("/{pid}/notice/{nid}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long nid) {
        return noticeService.deleteNotice(nid);
    }

}