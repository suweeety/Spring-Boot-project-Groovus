package com.groovus.www.dto;

import jakarta.validation.constraints.Null;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Log4j2
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterProjectDTO {

    private String pid;

    private String adminUid;

    private String projectName;

    private String projectPassword;

    private String projectDescription;

    private LocalDateTime regDate;

    private int memberCount;

    private List<String> projectMember;

}
