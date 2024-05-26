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
@ToString(exclude = "roleSet")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;

    private String uid;
    private String upw;

    private String uname;
    private String email;
    private boolean del;
    private  boolean social;

    @ElementCollection(fetch = FetchType.LAZY) //oneToMany 역할
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    @ManyToMany(mappedBy = "projectMember",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Project> projectList = new HashSet<>();

    public void changePassword(String upw){this.upw = upw; }
    public void changeDel(boolean del){

        this.del = del;
    }
    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }

    public void addProject(Project project){
        this.projectList.add(project);
    }
}