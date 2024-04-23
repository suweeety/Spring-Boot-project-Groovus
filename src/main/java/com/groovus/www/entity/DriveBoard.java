package com.groovus.www.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
// @ToString(exclude = "nickname")
public class DriveBoard extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 500, nullable = false)
    private String title;
  //  @ManyToOne (fetch = FetchType.LAZY)
    private String nickname;
    
    public void change(String title){
        this.title = title;
    } // 제목 수정
}
