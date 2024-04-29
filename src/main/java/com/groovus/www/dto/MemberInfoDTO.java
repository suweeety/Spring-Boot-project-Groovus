package com.groovus.www.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
@ToString
@Builder
public class MemberInfoDTO {

    private String mid;
    private String uid;

    private String uname;
    private String email;

    private boolean del;

}
