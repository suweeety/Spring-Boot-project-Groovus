package com.groovus.www.dto.notice;

import com.groovus.www.entity.Member;
import com.groovus.www.entity.Project;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class NoticeDTO {
    private Long nid;
    private Project project;
    private String title;
    private String content;
    private Member member;
    private String imageUrl;
    private LocalDateTime regDate;
}
