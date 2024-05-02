package com.groovus.www.service;

import com.groovus.www.dto.ProjectPageRequestDTO;
import com.groovus.www.dto.ProjectPageResponseDTO;
import com.groovus.www.dto.StatusHistoryDTO;
import com.groovus.www.dto.TaskDTO;
import com.groovus.www.entity.*;
import com.groovus.www.repository.MemberRepository;
import com.groovus.www.repository.ProjectRepository;
import com.groovus.www.repository.StatusHistoryRepository;
import com.groovus.www.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    private  final MemberRepository memberRepository;

    private final StatusHistoryRepository statusHistoryRepository;

    @Override
    public Long getTaskCount(Long pid) {

        return taskRepository.countTask(pid);
    }

    @Override
    @Transactional
    public Long registerTask(TaskDTO dto) {

        Task task = Task.builder()
                .taskTitle(dto.getTaskTitle())
                .taskWriter(dto.getTaskWriter())
                .taskContent(dto.getTaskContent())
                .responsibleMember(Long.parseLong(dto.getResponsibleMember()))
                .build();

        Optional<Project> result = projectRepository.findByIdWithMember(Long.parseLong(dto.getPid()));

        Project project = result.get();

        task.setProject(project);

        taskRepository.save(task);

        return task.getTid();
    }

    @Override
    public ProjectPageResponseDTO<TaskDTO> getTaskListWithPaging(ProjectPageRequestDTO pageRequestDTO, Long pid) {

        String [] types= pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("tid");

        Page<TaskDTO> result = taskRepository.searchAll(types,keyword,pageable,pid);

        List<TaskDTO> taskDTOList = result.stream().toList();

        return   ProjectPageResponseDTO.<TaskDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(taskDTOList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public TaskDTO readTask(String tid) {

        Optional<Task> result = taskRepository.getTaskByTid(Long.parseLong(tid));

        Task task = result.get();

        Optional<Member> memberResult = memberRepository.findByMid(task.getResponsibleMember());

        Member member = memberResult.get();

        TaskDTO taskDTO = TaskDTO.builder()
                .taskTitle(task.getTaskTitle())
                .taskWriter(task.getTaskWriter())
                .taskContent(task.getTaskContent())
                .regDate(task.getRegDate())
                .modDate(task.getModDate())
                .responsibleMember(member.getUid())
                .status(task.getStatus())
                .pid(task.getProject().getPid().toString())
                .tid(task.getTid().toString())
                .build();

        return taskDTO;
    }

    @Override
    public String changeTaskStatus(String tid, String status ,String uid ,String prevStatus ,String modDate) {
        //업무의 상태를 변경

        Optional<Task> result = taskRepository.getTaskByTid(Long.parseLong(tid));


        if(!result.isEmpty()){
            Task task = result.get();

            if(status.equals("TODO")){
                task.changeStatus(TaskStatus.TODO);
            }else if(status.equals("INPROGRESS")){
                task.changeStatus(TaskStatus.INPROGRESS);
            } else if (status.equals("BLOCK")) {
                task.changeStatus(TaskStatus.BLOCK);
            }else if(status.equals("DONE")){
                task.changeStatus(TaskStatus.DONE);
            }
            taskRepository.save(task);

            StatusHistory statusHistory = StatusHistory.builder()
                    .tid(task)
                    .status(status)
                    .prevStatus(prevStatus)
                    .uid(uid)
                    .modDate(modDate)
                    .build();

            statusHistoryRepository.save(statusHistory);

            return "success";
        }else{
            return "fail";
        }

    }

    @Override
    public List<StatusHistoryDTO> getHistory(String tid) {

        Optional<Task> result = taskRepository.getTaskByTid(Long.parseLong(tid));

        List<StatusHistory> statusHistoryList = statusHistoryRepository.getStatusHistoryBytid(result.get());

        if(!statusHistoryList.isEmpty()){

            List<StatusHistoryDTO> statusHistoryDTOList = statusHistoryList.stream().map(statusHistory -> {

                StatusHistoryDTO dto = StatusHistoryDTO.builder()
                        .hid(statusHistory.getHid().toString())
                        .tid(statusHistory.getTid().getTid().toString())
                        .uid(statusHistory.getUid())
                        .status(statusHistory.getStatus())
                        .prevStatus(statusHistory.getPrevStatus())
                        .modDate(statusHistory.getModDate())
                        .build();

                return dto;

            }).collect(Collectors.toList());

            return  statusHistoryDTOList;

        }else{
            return null;
        }

    }
}
