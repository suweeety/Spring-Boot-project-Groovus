package com.groovus.www.config;

import com.groovus.www.security.CustomUserDetailsService;
import com.groovus.www.security.handler.Custom403Handler;
import com.groovus.www.security.handler.CustomAuthenticationFailureHandler;
import com.groovus.www.security.handler.CustomSocialAuthenticationFailureHandler;
import com.groovus.www.security.handler.CustomSocialLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log4j2
@Configuration  //설정파일임을 표시
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true) // 어노테이션으로 권한 체크 할것인가??
public class CustomSecurityConfig {

    //의존성 주입
    private final DataSource dataSource;
    private final CustomUserDetailsService userDetailsService;

    private final OAuth2AuthorizedClientService authorizedClientService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        //패스워드 인코더
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new CustomSocialLoginSuccessHandler(passwordEncoder());
    }

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

        http.formLogin(form->{
           form.loginPage("/member/login");
           form.successForwardUrl("/"); //post로 받음
           form.failureHandler(authenticationFailureHandler());
        });

        //csrf토큰 비활성화
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

        //로그아웃 설정
        http.logout((logout)->logout
                .logoutSuccessUrl("/member/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"));

        //리멤버미 설정
        http.rememberMe(httpSecurityRememberMeConfigurer -> {
            httpSecurityRememberMeConfigurer
                    .alwaysRemember(false)
                    .key("12345678")
                    .tokenRepository(persistentTokenRepository())
                    .userDetailsService(userDetailsService)
                    .tokenValiditySeconds(60*60*24*30);

        });

        //카카오로그인을 위한 설정
        http.oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
            httpSecurityOAuth2LoginConfigurer.loginPage("/member/login")
                    .successHandler(authenticationSuccessHandler())
                    .failureHandler(customAuthenticationFailureHandler());
        }).logout(logout -> logout
                        .logoutSuccessUrl("/member/login") // 일반적인 로그아웃 성공 URL
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();

    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler(){return new CustomSocialAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new Custom403Handler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){return new CustomAuthenticationFailureHandler();}

    @Bean // 704 추가 토큰 발생용 (쿠키 값을 인코딩하기 위한 키, 필요한 정보를 저장하는 TokenRepository를 지정
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource); //데이터소스 객체 할당
        return repo;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        //정적으로 동작하는 파일들에는 굳이 시큐리티를 적용할 필요가 없으므로 메서드 추가 설정
        log.info("-------------------web configure--------------------");

        return (web)-> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());

    }
}
