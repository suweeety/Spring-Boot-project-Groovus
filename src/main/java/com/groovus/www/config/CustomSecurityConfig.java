package com.groovus.www.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log4j2
@Configuration  //설정파일임을 표시
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true) // 어노테이션으로 권한 체크 할것인가??
public class CustomSecurityConfig {


    //시큐리티설정하는 곳
    //SecurityFilterChain은 Spring Security에서 보안 처리를 위한 연속적인 필터들의 체인이다.
    //Spring의 동작 흐름
    //프로그램이 시작될 때, Spring은 @Configuration 애너테이션을 갖는 클래스를 찾아 설정 클래스로 인식한다.
    //설정 클래스 내에서 @Bean 애너테이션이 붙은 메서드를 찾아 실행하고, 그 결과로 반환되는 객체를 Spring IoC 컨테이너에 빈으로 등록한다.
    //securityFilterChain 메서드는 HttpSecurity 파라미터를 받아들여 웹 보안 관련 설정을 정의하고, http.build()를 통해 SecurityFilterChain 객체를 생성하여 반환한다.
    //반환된 SecurityFilterChain 객체는 Spring IoC 컨테이너에 빈으로 등록된다.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        log.info("------------------configuration-----------------------");

        http.csrf(httpSecurityCsrfConfigurer ->  httpSecurityCsrfConfigurer.disable() );

        return http.build();

    }

}
