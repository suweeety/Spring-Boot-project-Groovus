package com.groovus.www.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriveDTO {

    private Long bno;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String title;

    private String nickname;

    private LocalDateTime regdate;

    private LocalDateTime moddate;

}
