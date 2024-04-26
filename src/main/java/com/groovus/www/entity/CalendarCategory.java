package com.groovus.www.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name="tb_calendar_category")
@ToString
public class CalendarCategory {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long calCate_id;

    private String cal_category;

    public void change(Long calCate_id) {
        this.cal_category = cal_category;
    }
}
