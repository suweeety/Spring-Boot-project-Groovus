package com.groovus.www.service.notice;

import com.groovus.www.dto.MemberDTO;
import com.groovus.www.dto.ProjectPageRequestDTO;
import com.groovus.www.dto.ProjectPageResponseDTO;
import com.groovus.www.dto.ResponseDTO;
import com.groovus.www.dto.notice.NoticeRequestDTO;
import com.groovus.www.dto.notice.NoticeResponseDTO;
import org.springframework.http.ResponseEntity;

public interface NoticeService {

    //공지사항 생성
    public ResponseEntity<ResponseDTO<NoticeResponseDTO>> createNotice(NoticeRequestDTO noticeRequestDTO, MemberDTO memberDTO);

    //공지사항 조회
    public ProjectPageResponseDTO<NoticeResponseDTO> noticeList(ProjectPageRequestDTO PageRequestDTO);

    public NoticeResponseDTO readNotice(Long nid);

    //공지사항 수정
    ResponseEntity<ResponseDTO<NoticeResponseDTO>> updateNotice(NoticeRequestDTO noticeRequestDTO, Long nid);

    //공지사항 삭제
    public ResponseEntity<ResponseDTO<String>> deleteNotice(Long nid);

}
