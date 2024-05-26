package com.groovus.www.dto;

import com.groovus.www.entity.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatusHistoryDTO {

    private String hid;

    private String tid;

    private String status;

    private  String prevStatus;

    private String uid;

    private String modDate;

}
