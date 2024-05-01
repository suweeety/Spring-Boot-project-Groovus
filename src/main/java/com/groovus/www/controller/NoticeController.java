package com.groovus.www.controller;

import com.groovus.www.dto.PagingRequestDTO;
import com.groovus.www.dto.ResponseDTO;
import com.groovus.www.dto.notice.NoticeRequestDTO;
import com.groovus.www.dto.notice.NoticeResponseDTO;
import com.groovus.www.security.MemberDetailsImpl;
import com.groovus.www.service.NoticeService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NoticeController {
    private final NoticeService noticeService;

    //워크스페이스에 따라 구분
    //공지사항 생성
    @PostMapping("/{pid}/notice")
    public ResponseEntity<ResponseDTO<NoticeResponseDTO>> createNotice(@RequestBody NoticeRequestDTO noticeRequestDTO,
                                                                       @PathVariable Long pid,
                                                                       @AuthenticationPrincipal MemberDetailsImpl userDetails) {
        return noticeService.createNotice(noticeRequestDTO, pid, userDetails);
    }

    //공지사항 조회
    @GetMapping("/{pid}/notice")
    public ResponseEntity<ResponseDTO<List<NoticeResponseDTO>>> getNoticeWithPagination(@PathVariable Long pid,
                                                                                        @RequestBody PagingRequestDTO requestDTO) {
        return noticeService.getNoticeWithPagination(pid, requestDTO);
    }

    //공지사항 수정
    @PutMapping("/{pid}/notice/{nid}")
    public ResponseEntity<?> updateNotice(@RequestBody NoticeRequestDTO noticeRequestDTO, @PathVariable Long nid) {
        return noticeService.updateNotice(noticeRequestDTO, nid);
    }

    //공지사항 삭제
    @DeleteMapping("/{pid}/notice/{nid}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long nid) {
        return noticeService.deleteNotice(nid);
    }

}
