package com.groovus.www.controller;

import com.groovus.www.dto.chat.ChatRoomDto;
import com.groovus.www.dto.chat.ChatRoomMap;
import com.groovus.www.service.ChatService.ChatServiceMain;
import com.mysema.commons.lang.URLEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    // ChatService Bean 가져오기
    private final ChatServiceMain chatServiceMain;

    // 채팅방 생성
    // 채팅방 생성 후 다시 / 로 return
    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam("roomName") String name,
                             @RequestParam("roomPwd") String roomPwd,
                             @RequestParam("secretChk") String secretChk,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2") String maxUserCnt,
                             @RequestParam("chatType") String chatType,
                             @RequestParam("pid") String pid,
                             @RequestParam("projectName") String projectName,
                             RedirectAttributes rttr) {

        // log.info("chk {}", secretChk);

        // 매개변수 : 방 이름, 패스워드, 방 잠금 여부, 방 인원수
        ChatRoomDto room;

        log.info("===============여기는 채팅 컨트롤러==============");
        room = chatServiceMain.createChatRoom(name, roomPwd, Boolean.parseBoolean(secretChk), Integer.parseInt(maxUserCnt), chatType);


        log.info("CREATE Chat Room [{}]", room);

/*        rttr.addFlashAttribute("roomName", room);
        rttr.addFlashAttribute("pid",pid);
        rttr.addFlashAttribute("projectName",projectName);*/

        String encodeUrl = URLEncoder.encodeURL("/conference/roomlist/"+pid+"/"+projectName);
        return "redirect:"+encodeUrl;
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/room/{pid}/{projectName}")
    public String roomDetail(Model model, String roomId,@PathVariable("pid") String pid,@PathVariable("projectName")String projectName){

        log.info("roomId {}", roomId);

        // principalDetails 가 null 이 아니라면 로그인 된 상태!!
        /*
        if (principalDetails != null) {
            // 세션에서 로그인 유저 정보를 가져옴
            model.addAttribute("user", principalDetails.getUser());
        }
        */

        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);

        model.addAttribute("room", room);
        model.addAttribute("pid",pid);
        model.addAttribute("projectName",projectName);


        if (ChatRoomDto.ChatType.MSG.equals(room.getChatType())) {
            return "conference/chatroom";
        }else{
            model.addAttribute("uuid", UUID.randomUUID().toString());

            return "conference/rtcroom";
        }
    }

    // 채팅방 비밀번호 확인
    @PostMapping("/chat/confirmPwd/{roomId}")
    @ResponseBody
    public boolean confirmPwd(@PathVariable String roomId, @RequestParam String roomPwd){

        // 넘어온 roomId 와 roomPwd 를 이용해서 비밀번호 찾기
        // 찾아서 입력받은 roomPwd 와 room pwd 와 비교해서 맞으면 true, 아니면  false
        return chatServiceMain.confirmPwd(roomId, roomPwd);
    }

    // 채팅방 삭제
    @GetMapping("/chat/delRoom/{roomId}")
    public String delChatRoom(@PathVariable String roomId){

        // roomId 기준으로 chatRoomMap 에서 삭제, 해당 채팅룸 안에 있는 사진 삭제
        chatServiceMain.delChatRoom(roomId);

        return "redirect:/conference/roomlist";
    }

    // 유저 카운트
    @GetMapping("/chat/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable String roomId){

        return chatServiceMain.chkRoomUserCnt(roomId);
    }
}
