package com.groovus.www.dto;

import com.groovus.www.entity.CalendarAttach;
import com.groovus.www.entity.CalendarLink;
import com.groovus.www.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalendarDTO {

    //tb_calendar
    private Long cal_id; //

    private String cal_title; //

    private String cal_content; //

    private String cal_cate; //

    private String cal_startDate; //

    private String cal_endDate;

    private List<CalendarAttach> cal_attach;

    private List<CalendarLink> cal_link_list;

    private List<Member> cal_members;

    private Long pid; // 프로젝트 번호

    private String create_user_id;

    private String update_user_id;

}
