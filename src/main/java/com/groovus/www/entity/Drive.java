package com.groovus.www.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
public class Drive extends com.groovus.www.entity.BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 500, nullable = false)
    private String title;

    private String nickname;

    public void change(String title){
        this.title = title;
    }

    @OneToMany(mappedBy = "drive", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true )
    @Builder.Default
    @BatchSize(size = 20)
    private Set<DriveImage> imageSet = new HashSet<>();

    public void addImage(String uuid, String fileName){

        DriveImage driveImage = DriveImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .drive(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(driveImage);
    }

    public void clearImages(){

        imageSet.forEach(driveImage -> driveImage.changeDrive(null));

        this.imageSet.clear();
    }
}