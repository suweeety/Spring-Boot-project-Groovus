package com.groovus.www.dto;

import com.groovus.www.entity.CalendarAttach;
import com.groovus.www.entity.Project;
import com.groovus.www.entity.User;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    private String cal_color;

    private String cal_period;
    private String cal_dday;
    private String cal_link;
    private String cal_todo;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    //tb_calendarAttach
    private Set<CalendarAttach> cal_attach;

    //tb_user
    private List<User> uIds;
    @NotEmpty
    private User create_user_id;
    private List<User> update_user_id;

    //tb_project
    private Project pId;
}
