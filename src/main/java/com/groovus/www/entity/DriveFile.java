package com.groovus.www.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "driveBoard")
public class DriveFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fnum;

    private String uuid;

    private String fName;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private DriveBoard driveBoard;
}
