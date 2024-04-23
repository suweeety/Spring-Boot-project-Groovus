package com.groovus.www.service;

import com.groovus.www.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean chagePwForSocialLogin(String mid, String newPw) {

        int result =memberRepository.updatePassword(passwordEncoder.encode(newPw),Long.parseLong(mid));

        if(result<=0){

            return false;

        }else {
            return true;
        }
    }
}
