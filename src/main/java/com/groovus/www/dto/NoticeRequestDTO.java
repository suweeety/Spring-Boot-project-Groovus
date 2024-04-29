package com.groovus.www.dto;

import com.groovus.www.entity.Member;
import com.groovus.www.entity.Project;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRequestDTO {
    private String title;
    private String content;
    private String imageUrl;
}
