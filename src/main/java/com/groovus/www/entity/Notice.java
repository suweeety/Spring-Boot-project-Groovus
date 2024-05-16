package com.groovus.www.entity;

import com.groovus.www.dto.notice.NoticeRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.lang.management.RuntimeMXBean;

@ToString(exclude = "project")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nid;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project; //속해있는 프로젝트 pid

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    private String noticeWriter; //작성자

    @Builder.Default
    private boolean del =false ; //삭제여부

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid", nullable = false)
    private Member member;


    public void updateNotice(NoticeRequestDTO dto) { //공지사항 업데이트
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void setProject(Project project){
        this.project = project;
    }

    public void changeDel(boolean del) {
        this.del = del;
    }
}
