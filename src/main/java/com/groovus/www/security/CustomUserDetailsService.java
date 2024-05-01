package com.groovus.www.security;


import com.groovus.www.dto.MemberDTO;
import com.groovus.www.entity.Member;
import com.groovus.www.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByException:"+username);

        Optional<Member> result = memberRepository.getWithRoles(username);

        if(result.isEmpty()){
            throw new UsernameNotFoundException("username not found");
        }

        Member member = result.get();

        MemberDTO memberDTO = new MemberDTO(member.getUid(),member.getUpw(),member.getMid(),member.getUname(),
                member.getEmail(),member.isDel(),false,member.getRoleSet().stream().map(memberRole -> new SimpleGrantedAuthority("ROLR_"+memberRole.name())).collect(Collectors.toList()));


        return memberDTO;
    }

}
