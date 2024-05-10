package com.groovus.www.service.notice;

import com.groovus.www.dto.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class NoticeServiceImpl implements NoticeService{

    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;

    //공지사항 생성
    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<NoticeResponseDTO>> createNotice(NoticeRequestDTO noticeRequestDTO, MemberDTO memberDTO) {

        Member member = memberRepository.findByMid(memberDTO.getMid()).get();

        Notice notice = Notice.builder()
                .title(noticeRequestDTO.getTitle())
                .content(noticeRequestDTO.getContent())
                .member(member)
                .build();

        noticeRepository.save(notice);
        NoticeResponseDTO noticeResponseDTO = NoticeResponseDTO.of(notice);

        return new ResponseEntity<>(ResponseDTO.success(noticeResponseDTO), HttpStatus.OK);
    }

    //공지사항 조회
    @Override
    @Transactional //
    public ProjectPageResponseDTO<NoticeResponseDTO> noticeList(ProjectPageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("nid");

        Page<NoticeResponseDTO> result = noticeRepository.searchAll(types, keyword, pageable);

        List<NoticeResponseDTO> noticeDTOList = result.stream().toList();

        return ProjectPageResponseDTO.<NoticeResponseDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(noticeDTOList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<NoticeResponseDTO>> readNotice(Long nid) {

        Optional<Notice> notice = noticeRepository.findByNid(nid);

        NoticeResponseDTO noticeResponseDTO = NoticeResponseDTO.builder()
                .nid(notice.get().getNid())
                .title(notice.get().getTitle())
                .content(notice.get().getContent())
                .uname(notice.get().getMember().getUname())
                .uid(notice.get().getMember().getUid())
                .regDate(notice.get().getRegDate())
                .modDate(notice.get().getModDate())
                .build();
        return new ResponseEntity<>(ResponseDTO.success(noticeResponseDTO), HttpStatus.OK);
    }


    //공지사항 수정
    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<NoticeResponseDTO>> updateNotice(NoticeRequestDTO noticeRequestDTO, Long nid) {
        Notice notice = isNoticeExist(nid);
        notice.updateNotice(noticeRequestDTO);
        return new ResponseEntity<>(ResponseDTO.success(NoticeResponseDTO.of(notice)), HttpStatus.OK);
    }




    //공지사항 삭제
    @Override
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
