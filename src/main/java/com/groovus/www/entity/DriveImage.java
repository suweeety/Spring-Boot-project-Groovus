package com.groovus.www.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "drive")
public class DriveImage implements Comparable<DriveImage> {

    @Id
    private String uuid;

    private String fileName;

    private int ord; // 순번

    @ManyToOne
    private Drive drive;

    @Override
    public int compareTo(DriveImage other) {
        return this.ord - other.ord;
    }

    public void changeDrive(Drive drive){
        this.drive = drive;
    }
}