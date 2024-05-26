package com.groovus.www.dto;

import lombok.*;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDTO {

    private String mid;
    private String uid;

    private String uname;
    private String email;

    private boolean del;

}
