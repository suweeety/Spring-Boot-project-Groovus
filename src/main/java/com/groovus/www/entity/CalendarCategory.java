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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cal_cate_id;

    private String cal_category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Calendar calendar;

    public void change(String cal_category) {
        this.cal_category = cal_category;
    }
}
