package com.groovus.www.dto.notice;

import com.groovus.www.entity.Member;
import com.groovus.www.entity.Notice;
import com.groovus.www.entity.Project;
import com.groovus.www.mapper.GenericMapper;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoticeRequestDTO {
    private String title;
    private String content;
    private String pid;
    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .content(content)
                .build();
    }

}