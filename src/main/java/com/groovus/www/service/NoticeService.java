package com.groovus.www.service;

import com.groovus.www.dto.PagingRequestDTO;
import com.groovus.www.dto.ResponseDTO;
import com.groovus.www.dto.notice.NoticeRequestDTO;
import com.groovus.www.dto.notice.NoticeResponseDTO;
import com.groovus.www.entity.Notice;
import com.groovus.www.entity.Project;
import com.groovus.www.exception.ErrorCode;
import com.groovus.www.exception.RequestException;
import com.groovus.www.repository.NoticeRepository;
import com.groovus.www.repository.ProjectRepository;
import com.groovus.www.security.CustomUserDetailsService;
import com.groovus.www.security.MemberDetailsImpl;
import groovy.util.logging.Slf4j;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final ProjectRepository projectRepository;

    //공지사항 생성
    @Transactional
    public ResponseEntity<ResponseDTO<NoticeResponseDTO>> createNotice(NoticeRequestDTO noticeRequestDTO,
                                                                       Long pid,
                                                                       MemberDetailsImpl userDetails) {

        Project findProject = projectRepository.findById(pid)
                .orElseThrow(() -> new RequestException(ErrorCode.PROJECT_NOT_FOUND_404));

        Notice notice = Notice.builder()
                .title(noticeRequestDTO.getTitle())
                .content(noticeRequestDTO.getContent())
                .member(userDetails.getMember())
                .project(findProject)
                .build();

        noticeRepository.save(notice);
        NoticeResponseDTO noticeResponseDTO = NoticeResponseDTO.of(notice);

        return new ResponseEntity<>(ResponseDTO.success(noticeResponseDTO), HttpStatus.OK);
    }

    //공지사항 조회
    @Transactional //
    public ResponseEntity<ResponseDTO<List<NoticeResponseDTO>>> getNoticeWithPagination(Long pid, PagingRequestDTO requestDTO) {
    
        isProjectExist(pid); //프로젝트 존재하는지 확인
        Long cursor; //페이징 커서 변수 선언
        if (requestDTO == null) { //id를 기준으로 생성일 내림차순으로 첫 번째 공지사항을 찾음, dto가 없으면 ID가 1인 공지사항 생성
            Notice notice = noticeRepository.findFirstByProjectPidOrderByRegDateDesc(pid)
                    .orElse(Notice.builder().nid(1L).build());
            cursor = notice.getNid() + 1; //커서를 다음 공지사항의 id로 설정

        } else cursor = requestDTO.getCursorId(); //요청 DTO에서 커서 가져옴

        //페이징 처리된 공지사항 슬라이스를 조회
        Slice<Notice> noticeSlice =
                noticeRepository
                        .findAllByNidBeforeAndProject_PidOrderByRegDateDesc(
                                cursor,
                                pid,
                                PageRequest.of(0, 5) //첫 페이지, 페이지 크기 5로 설정
                        );
        //공지사항 슬라이스를 DTO리스트로 변환
        List<NoticeResponseDTO> noticeDtoList =
                noticeSlice.stream()
                        .map(NoticeResponseDTO::of) //공지사항을 공지사항 응답 DTO로 변환
                        .collect(Collectors.toList()); //리스트로 변환

        //성공 응답 반환
        return new ResponseEntity<>(ResponseDTO.success(noticeDtoList), HttpStatus.OK);

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

    //pid가 없을 때 에러코드 실행
    private Project isProjectExist(Long pid) {
        return projectRepository.findById(pid).orElseThrow(() -> new RequestException(ErrorCode.INVALID_PARAMETER));
    }

    //nid가 없을 때 에러코드 실행
    private Notice isNoticeExist(Long nid) {
        return noticeRepository.findById(nid).orElseThrow(() -> new RequestException(ErrorCode.INVALID_PARAMETER));
    }


}
