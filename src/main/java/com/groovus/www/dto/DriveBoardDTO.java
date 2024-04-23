package com.groovus.www.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriveBoardDTO {

    private Long bno;

    private String title;

    private String nickname;

    private LocalDateTime regdate;

    private LocalDateTime moddate;
}
