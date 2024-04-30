package com.groovus.www.dto;

import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Data
@Log4j2
@ToString
public class RegisterProjectDTO {

    private String projectName;

    private String projectPassword;

    private String projectDescription;

    private List<String> projectMember;

}
