package com.groovus.www.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name="tb_user")
@ToString
public class User extends BaseEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long mId;

    @JsonIgnore
    @Column(nullable = false)
    private String mPw;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private boolean del;

    @Column(nullable = false)
    private boolean social;

    /*
    @OneToMany //수정필요
    @Column(nullable = false)
    private Set<> roleSet;

    @ManyToMany
    @Column(nullable = false)
    private List<String> projectList;
    */



    /*@Column
    private String profileImage;

    public void updateProfileImage(String profileImage){
        this.profileImage=profileImage;
    }*/

}