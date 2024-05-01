package com.groovus.www.entity;

import com.groovus.www.dto.notice.NoticeRequestDTO;
import groovy.transform.ToString;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.userdetails.User;

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

    @Lob
    @Column(nullable = false)
    private String content;

    @JsonIgnore //JSON 직렬화 시 해당 필드를 무시
    @ManyToOne(fetch = FetchType.LAZY) //다대일 관계
    @JoinColumn(name = "pid", nullable = false) //외래키 지정
    private Project project;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid", nullable = false)
    private Member member;

    private String imageUrl = null; //이미지 URL을 저장

    public void updateNotice(NoticeRequestDTO dto) { //공지사항 업데이트
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

}
