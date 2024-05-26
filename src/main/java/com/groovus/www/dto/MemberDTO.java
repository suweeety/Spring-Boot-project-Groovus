package com.groovus.www.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.lang.management.MemoryManagerMXBean;
import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class MemberDTO extends User implements OAuth2User {

    private Long mid;
    private String uid;
    private String upw;

    private String uname;
    private String email;

    private boolean del;
    private  boolean social;

    //소셜 로그인 정보
    private Map<String, Object> props;


    public MemberDTO(String username, String password, Long mid , String uname,String email, boolean del, boolean social, Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);

        this.mid= mid;
        this.uid= username;
        this.upw =password;
        this.email=email;
        this.uname=uname;
        this.del=del;
        this.social=social;
    }

    public Map<String,Object> getAttributes(){return this.getProps();}

    @Override
    public String getName(){return this.uid;}

}
