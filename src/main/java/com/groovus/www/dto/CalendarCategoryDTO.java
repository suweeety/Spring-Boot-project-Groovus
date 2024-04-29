package com.groovus.www.dto;

import com.groovus.www.entity.CalendarCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarCategoryDTO {

    private Long cal_cate_id;

    private String cal_category;
}
