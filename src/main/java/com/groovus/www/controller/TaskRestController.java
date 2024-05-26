package com.groovus.www.controller;

import com.groovus.www.dto.StatusHistoryDTO;
import com.groovus.www.dto.TaskDTO;
import com.groovus.www.dto.TaskReReplyDTO;
import com.groovus.www.dto.TaskReplyDTO;
import com.groovus.www.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskService taskService;

    @PostMapping("/task/countall")
    public ResponseEntity<List<Long>> getTaskCount(String pid , String mid, String uid){
        List<Long> countList = taskService.getTaskCount(Long.parseLong(pid),Long.parseLong(mid),uid);

        return new ResponseEntity<>(countList, HttpStatus.OK);

    }

    @PostMapping ("/task/view")
    public ResponseEntity<TaskDTO> readTask(String tid){

        log.info("=============================");
        log.info(tid);
        log.info("=============================");

        TaskDTO taskDTO = taskService.readTask(tid);

        return new ResponseEntity<>(taskDTO,HttpStatus.OK);
    }

    @PostMapping("/task/changestatus")
    public ResponseEntity<List<StatusHistoryDTO>> changeStatus(String tid , String status , String uid ,String prevStatus){

        //날짜 구하기
        LocalDate now = LocalDate.now();
        //날짜 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        //포맷 적용
        String formatedNow = now.format(formatter);

        String result  = taskService.changeTaskStatus(tid,status,uid,prevStatus,formatedNow);

        if(result.equals("success")){

            List<StatusHistoryDTO> statusHistoryDTOList = taskService.getHistory(tid);

            log.info("========================상태 리스트=====================");
            log.info(statusHistoryDTOList);
            log.info("===================================================");


            if(statusHistoryDTOList != null){

                return new ResponseEntity<>(statusHistoryDTOList,HttpStatus.OK);

            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    @PostMapping("/task/history")
    public ResponseEntity<List<StatusHistoryDTO>> getHistory(String tid){

        //업무 상태변경 이력 리스트 반환 컨트롤러

        List<StatusHistoryDTO> list = taskService.getHistory(tid);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping("/task/register/reply")
    public ResponseEntity<List<TaskReplyDTO>> registerReply(@RequestParam("readTaskTid") String tid, String uid, String replyContent){

        //업무에 댓글 달기

        TaskReplyDTO taskReplyDTO = TaskReplyDTO.builder()
                .replyContent(replyContent)
                .tid(tid)
                .uid(uid)
                .build();

        int result = taskService.registerReply(taskReplyDTO);

        if(result>0){

            List<TaskReplyDTO> taskReplyDTOList = taskService.getTaskRepltList(tid);

           return new ResponseEntity<>(taskReplyDTOList,HttpStatus.OK);

        }else{
            return new ResponseEntity<>(null,HttpStatus.OK);
        }

    }

    @PostMapping("/task/replyList")
    public ResponseEntity<List<TaskReplyDTO>> getReplyList(String tid){
        //댓글 리스트를 가져옴

        List<TaskReplyDTO> taskReplyDTOList = taskService.getTaskRepltList(tid);

        if(taskReplyDTOList!=null){
            return new ResponseEntity<>(taskReplyDTOList,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.OK);
        }

    }

    @PostMapping("/task/register/rereply")
    public ResponseEntity<String> registerReReply(String parentRid ,String uid , String replyText){
        //대댓글 등록
        TaskReReplyDTO taskReReplyDTO = TaskReReplyDTO.builder()
                .replyText(replyText)
                .rid(parentRid)
                .uid(uid)
                .build();

        int result = taskService.registerReReply(taskReReplyDTO);

        if(result>0){
            return new ResponseEntity<>("success",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("fail",HttpStatus.OK);
        }
    }


    @PostMapping("/task/rereplyList")
    public ResponseEntity<List<TaskReReplyDTO>> getReReplyList(String rid){

        List<TaskReReplyDTO> replyDTOList = taskService.getTaskReReplyList(rid);

        return new ResponseEntity<>(replyDTOList, HttpStatus.OK);
    }

    @PostMapping("/task/modify")
    public ResponseEntity<String> modifyTask(TaskDTO taskDTO){

        int result =taskService.modifyTask(taskDTO);

        if(result>0){
            return new ResponseEntity<>("success",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("fail",HttpStatus.OK);
        }

    }

    @DeleteMapping("/task/delete")
    public ResponseEntity<String> deleteTask(String tid){

            int result = taskService.deleteTask(tid);

            if(result>0){
                return new ResponseEntity<String>("success",HttpStatus.OK);
            }else {
                return new ResponseEntity<String>("fail",HttpStatus.OK);
            }

    }
}
