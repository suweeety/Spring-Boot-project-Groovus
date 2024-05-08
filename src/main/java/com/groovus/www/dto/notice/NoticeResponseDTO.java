package com.groovus.www.dto.notice;

import com.groovus.www.entity.Notice;
import groovy.util.logging.Log4j2;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Log4j2
@Data
@ToString
public class NoticeResponseDTO {

    private Long nid; //공지사항 id
    private String title; //공지사항 제목
    private String content; //공지사항 내용
    private String uid; //회원 id
    private String uname; //회원이름
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public static NoticeResponseDTO of(Notice notice) {
        return NoticeResponseDTO.builder()
                .nid(notice.getNid())
                .uname(notice.getMember().getUname())
                .title(notice.getTitle())
                .content(notice.getContent())
                .uid(notice.getMember().getUid())
                .regDate(notice.getRegDate())
                .modDate(notice.getModDate())
                .build();
    }

}

