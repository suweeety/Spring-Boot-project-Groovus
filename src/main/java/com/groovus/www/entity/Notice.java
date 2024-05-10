package com.groovus.www.entity;

import com.groovus.www.dto.notice.NoticeRequestDTO;
import groovy.transform.ToString;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@ToString
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nid;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid", nullable = false)
    private Member member;


    public void updateNotice(NoticeRequestDTO dto) { //공지사항 업데이트
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

}
