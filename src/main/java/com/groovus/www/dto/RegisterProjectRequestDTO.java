package com.groovus.www.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Log4j2
@ToString
@Builder
public class RegisterProjectRequestDTO {


    private String  adminUid;

    private String projectName;

    private String projectPassword;

    private String projectDescription;

    private List<String> projectMember;

}
