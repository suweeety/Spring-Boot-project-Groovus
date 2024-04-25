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

    private String cal_category;

}
