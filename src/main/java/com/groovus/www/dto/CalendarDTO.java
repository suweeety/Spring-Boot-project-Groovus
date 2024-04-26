package com.groovus.www.dto;

import com.groovus.www.entity.CalendarAttach;
import com.groovus.www.entity.CalendarCategory;
import com.groovus.www.entity.Project;
import com.groovus.www.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    private CalendarCategory cal_category;

    private String cal_startDate;

    private String cal_endDate;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

}
