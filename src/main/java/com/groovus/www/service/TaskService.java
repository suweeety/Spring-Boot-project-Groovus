package com.groovus.www.service;

import com.groovus.www.dto.ProjectPageRequestDTO;
import com.groovus.www.dto.ProjectPageResponseDTO;
import com.groovus.www.dto.StatusHistoryDTO;
import com.groovus.www.dto.TaskDTO;
import com.groovus.www.entity.Task;

import java.util.List;

public interface TaskService {

    //pid로 업무 개수를 가져오는 메서드 (업무 번호 보여줄 때 사용)
    public Long getTaskCount(Long pid);

    //업무 등록 메서드
    public Long registerTask(TaskDTO dto);

    //업무 리스트를 가져오는 메서드
    public ProjectPageResponseDTO<TaskDTO> getTaskListWithPaging(ProjectPageRequestDTO pageRequestDTO , Long pid);

    //tid로 업무 객체를 가져오는 메서드
    public TaskDTO readTask(String tid);

    //tid와 status값으로 상태 변경하는 메서드
    public String changeTaskStatus(String tid, String status,String uid ,String prevStatus,String modDate);

    //tid로 변경 이력을 가져오는 메서드
    public List<StatusHistoryDTO> getHistory(String tid);


}
