package com.groovus.www.dto;

import com.groovus.www.entity.Calendar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarAttachDTO {

    private Long calAtt_id;
    private String filename;
    private String uuid;
    private String uploadPath;

    //tb_calendar
    private Calendar cal_id;
}
