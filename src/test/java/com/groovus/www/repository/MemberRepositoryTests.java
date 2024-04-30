package com.groovus.www.repository;

import com.groovus.www.entity.Member;
import com.groovus.www.entity.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMember(){

        Member member = Member.builder()
                .uid("jeongeun")
                .upw(passwordEncoder.encode("1111"))
                .email("jeongeun@aaa.com")
                .uname("김정은")
                .build();

        member.addRole(MemberRole.USER);

        memberRepository.save(member);
    }


}
