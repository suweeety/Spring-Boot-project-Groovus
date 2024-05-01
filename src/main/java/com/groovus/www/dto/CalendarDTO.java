package com.groovus.www.dto;

import com.groovus.www.entity.CalendarAttach;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDTO {

    //tb_calendar
    private Long cal_id;

    @NotEmpty
    private String cal_title;

    @NotEmpty
    private String cal_content;

    private String cal_cate;

    private String cal_startDate;

    private String cal_endDate;

    private List<CalendarAttach> cal_attach;

    private String cal_link;

}
