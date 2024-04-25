package com.groovus.www.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    private List<DriveFileDTO> driveFileDTOList = new ArrayList<>();
}
