package com.groovus.www.service;

import com.groovus.www.dto.MemberDTO;
import com.groovus.www.dto.PagingRequestDTO;
import com.groovus.www.dto.ResponseDTO;
import com.groovus.www.dto.notice.NoticeRequestDTO;
import com.groovus.www.dto.notice.NoticeResponseDTO;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Notice;
import com.groovus.www.exception.ErrorCode;
import com.groovus.www.exception.RequestException;
import com.groovus.www.repository.MemberRepository;
import com.groovus.www.repository.NoticeRepository;
import groovy.util.logging.Slf4j;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class NoticeService {

    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;

    //공지사항 생성
    @Transactional
    public ResponseEntity<ResponseDTO<NoticeResponseDTO>> createNotice(NoticeRequestDTO noticeRequestDTO, MemberDTO memberDTO) {

        Member member = memberRepository.findByMid(memberDTO.getMid()).get();

        Notice notice = Notice .builder()
                .title(noticeRequestDTO.getTitle())
                .content(noticeRequestDTO.getContent())
                .member(member)
                .build();

        noticeRepository.save(notice);
        NoticeResponseDTO noticeResponseDTO = NoticeResponseDTO.of(notice);

        return new ResponseEntity<>(ResponseDTO.success(noticeResponseDTO), HttpStatus.OK);
    }

    //공지사항 조회
    @Transactional //
    public List<NoticeResponseDTO> noticeList(PagingRequestDTO requestDTO) {
    
        Long cursor; //페이징 커서 변수 선언

        if (requestDTO == null) { // 생성일자를 기준으로 오름차순으로 첫 번째 공지사항을 가져옴, dto가 없으면 ID가 1인 공지사항 생성
            Notice notice = noticeRepository.findFirstByOrderByRegDateAsc();
            cursor = notice.getNid() + 1; //커서를 다음 공지사항의 id로 설정

        } else cursor = requestDTO.getCursorId(); //요청 DTO에서 커서 가져옴

        //공지사항 조회
        List<Notice> noticeList = noticeRepository.findAllByOrderByRegDateDesc();

        List<NoticeResponseDTO> noticeResponseDTO = new ArrayList<>();

        for (Notice notice : noticeList) {
            noticeResponseDTO.add(NoticeResponseDTO.builder()
                    .nid(notice.getNid())
                    .uname(notice.getMember().getUname())
                    .title(notice.getTitle())
                    .content(notice.getContent())
                    .uid(notice.getMember().getUid())
                    .regDate(notice.getRegDate())
                    .modDate(notice.getModDate())
                    .build());

        }

        return noticeResponseDTO;

    }

    //공지사항 수정
    @Transactional
    public ResponseEntity<ResponseDTO<NoticeResponseDTO>> updateNotice(NoticeRequestDTO noticeRequestDTO, Long nid) {
        Notice notice = isNoticeExist(nid);
        notice.updateNotice(noticeRequestDTO);
        return new ResponseEntity<>(ResponseDTO.success(NoticeResponseDTO.of(notice)), HttpStatus.OK);
    }
    //공지사항 삭제
    @Transactional
    public ResponseEntity<ResponseDTO<String>> deleteNotice(Long nid) {
        isNoticeExist(nid);
        noticeRepository.deleteById(nid);
        return new ResponseEntity<>(ResponseDTO.success(null), HttpStatus.OK);
    }

    //nid가 없을 때 에러코드 실행
    private Notice isNoticeExist(Long nid) {
        return noticeRepository.findById(nid).orElseThrow(() -> new RequestException(ErrorCode.INVALID_PARAMETER));
    }


}
