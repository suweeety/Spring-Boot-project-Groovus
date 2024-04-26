package com.groovus.www.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "projectMember")
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    private String  adminUid;

    private String projectName;
    private String projectPassword;
    private String projectDescription;

    @ManyToMany(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Member> projectMember = new HashSet<>();

    public void addMember(Member member){
        this.projectMember.add(member);
    }
}
