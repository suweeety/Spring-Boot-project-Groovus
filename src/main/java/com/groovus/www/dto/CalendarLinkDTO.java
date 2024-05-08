package com.groovus.www.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalendarLinkDTO {

    private Long cal_link_id;
    private String cal_link_title;
    private String cal_link;
}
