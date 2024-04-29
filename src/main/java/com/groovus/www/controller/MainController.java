package com.groovus.www.controller;

import com.groovus.www.service.ChatService.ChatServiceMain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final ChatServiceMain chatServiceMain;

    // 채팅 리스트 화면

    // 스프링 시큐리티의 로그인 유저 정보는 Security 세션의 PrincipalDetails 안에 담긴다
    // 정확히는 PrincipalDetails 안에 ChatUser 객체가 담기고, 이것을 가져오면 된다.
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/conference/roomlist")
    public String goChatRoom(Model model){

        model.addAttribute("list", chatServiceMain.findAllRoom());
        // principalDetails 가 null 이 아니라면 로그인 된 상태!!
        /*
        if (principalDetails != null) {
            // 세션에서 로그인 유저 정보를 가져옴
            model.addAttribute("user", principalDetails.getUser());
        }*/

//        model.addAttribute("user", "hey");
        log.debug("SHOW ALL ChatList {}", chatServiceMain.findAllRoom());
        return "/conference/roomlist";
    }

}
