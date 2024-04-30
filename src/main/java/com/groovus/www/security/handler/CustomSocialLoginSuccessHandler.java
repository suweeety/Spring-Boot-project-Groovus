package com.groovus.www.security.handler;

import com.groovus.www.dto.MemberDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("------------------------------------");
        log.info("CustomLoginSuccessHadler onAuthenticationSuccess....................");
        log.info(authentication.getPrincipal());

        MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();

        String encodePw = memberDTO.getUpw();

        //소셜 로그인이고 회원의 패스워드가 1111이면 초기로그인이면 정보 변경하러 이동
        if(memberDTO.isSocial() && (memberDTO.getUpw().equals("1111")|| passwordEncoder.matches("1111",memberDTO.getUpw()))){

            log.info("Should Change Password");

            log.info("Redirect to Member Modify");

            response.sendRedirect("/member/socialmodify");

            return;

        }else {
            response.sendRedirect("/");
        }
    }
}