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
    private Long cal_cate_id;

    private String cal_category;

    @ManyToOne
    private Calendar calendar;

    public void change(String cal_category) {
        this.cal_category = cal_category;
    }
}
