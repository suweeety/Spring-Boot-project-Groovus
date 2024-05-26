package com.groovus.www.service;

import com.groovus.www.dto.*;
import com.groovus.www.entity.Task;

import java.util.List;

public interface TaskService {

    //pid로 업무 개수를 가져오는 메서드 (업무 번호 보여줄 때 사용)
    public List<Long> getTaskCount(Long pid , Long mid , String uid);

    //업무 등록 메서드
    public Long registerTask(TaskDTO dto);

    //업무 리스트를 가져오는 메서드
    public ProjectPageResponseDTO<TaskDTO> getTaskListWithPaging(ProjectPageRequestDTO pageRequestDTO , Long pid);

    //tid로 업무 객체를 가져오는 메서드
    public TaskDTO readTask(String tid);

    //TaskDTO 객체로 업무를 수정하는 메서드
    public int modifyTask(TaskDTO taskDTO);

    //tid와 status값으로 상태 변경하는 메서드
    public String changeTaskStatus(String tid, String status,String uid ,String prevStatus,String modDate);

    //tid로 변경 이력을 가져오는 메서드
    public List<StatusHistoryDTO> getHistory(String tid);

    //업무 삭제 메서드 (del = false처리)
    public int deleteTask(String tid);

    //댓글을 저장하는 메서드
    public int registerReply(TaskReplyDTO replyDTO);

    //tid로 댓글 리스트를 가져오는 메서드
    public List<TaskReplyDTO> getTaskRepltList(String tid);

    //대댓글을 저장하는 메서드
    public int registerReReply(TaskReReplyDTO taskReReplyDTO);

    //rid로 대댓글 리스트를 가져오는 메서드
    public List<TaskReReplyDTO> getTaskReReplyList(String rid);

    //내가쓴 댓글들 가져오기
    List<TaskReplyDTO> getMyReplyList(String uid , Long pid);
    List<TaskReReplyDTO> getMyReReplyList(String uid , Long pid);

}
