package com.groovus.www.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

//    @Column(columnDefinition = "TIMESTAMP")
//    private LocalDateTime deletedAt; // 삭제된 시간

    public Bin(String title) {
        this.title = title;
//        this.deletedAt = LocalDateTime.now();
    }
}
