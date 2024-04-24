package com.groovus.www.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_project")
@ToString
public class Project extends BaseEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pId;

    /*
    private user
    */

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean del;

}