package com.groovus.www.security;

import com.groovus.www.dto.MemberDTO;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.MemberRole;
import com.groovus.www.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName(); //kakao..

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String,Object> paramMap = oAuth2User.getAttributes();

        log.info("==================소셜 로그인 정보==================");
        log.info(paramMap);
        log.info("==================소셜 로그인 정보==================");

        String email =null;
        String name = null;

        switch (clientName) {

            case "kakao":
                email=getKaKaoEmail(paramMap);
                name=getKaKaoName(paramMap);
                break;
        }


        return generateDTO(email,name,paramMap);
    }

    private String getKaKaoName(Map<String, Object> paramMap) {
        Object value = paramMap.get("properties");

        LinkedHashMap nameMap = (LinkedHashMap) value;

        String name = (String) nameMap.get("nickname");

        return name;
    }

    private String getKaKaoEmail(Map<String, Object> paramMap) {

        Object value = paramMap.get("kakao_account");

        LinkedHashMap accountMap = (LinkedHashMap) value;

        String email = (String) accountMap.get("email"); // Object타입이라 변환 필요함

        return email;
    }

    private MemberDTO generateDTO(String email,String name,Map<String,Object> params){

        Optional<Member> result = memberRepository.findByUid(email);

        //데이터베이스에 해당 이메일 사용자가 없다면
        if(result.isEmpty()){
            //회원추가
            Member member = Member.builder()
                    .uid(email)
                    .email(email)
                    .uname(name)
                    .upw(passwordEncoder.encode("1111"))
                    .social(true)
                    .build();

            member.addRole(MemberRole.USER);
            memberRepository.save(member);

            MemberDTO memberDTO = new MemberDTO(member.getUid(), member.getUpw(), member.getMid(), member.getUname(), member.getEmail(), false, true, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            memberDTO.setProps(params);

            return memberDTO;

        }else{
            Member member =result.get();

                MemberDTO memberDTO = new MemberDTO(member.getUid(), member.getUpw(), member.getMid(), member.getUname(), member.getEmail(), member.isDel(), member.isSocial(), member.getRoleSet().stream()
                        .map(memberRole -> new SimpleGrantedAuthority("ROLE_"+memberRole.name())).collect(Collectors.toList()));

                return memberDTO;


        }
    }
}
