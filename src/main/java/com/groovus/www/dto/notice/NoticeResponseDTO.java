package com.groovus.www.dto.notice;

import com.groovus.www.entity.Member;
import com.groovus.www.entity.Notice;
import com.groovus.www.entity.Project;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class NoticeResponseDTO {
    private Long nid; //공지사항 id
    private Long pid; //프로젝트 id
    private String title; //공지사항 제목
    private String content; //공지사항 내용
    private String uid; //회원 id
    private String uname; //회원이름
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public static NoticeResponseDTO of(Notice notice) {
        return NoticeResponseDTO.builder()

                .build();
    }

}

