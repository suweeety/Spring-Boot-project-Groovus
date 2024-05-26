package com.groovus.www.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriveAllDTO {

    private Long bno;

    private String title;

    private String nickname;

    private LocalDateTime regdate;

    private LocalDateTime moddate;

    private List<DriveImageDTO> driveImages;
}