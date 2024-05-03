package com.groovus.www.service;

import com.groovus.www.dto.*;
import com.groovus.www.entity.*;
import com.groovus.www.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.image.RGBImageFilter;
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

    private final TaskReplyRepository taskReplyRepository;

    private final TaskReReplyRepository taskReReplyRepository;

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

    @Override
    public int registerReply(TaskReplyDTO taskReplyDTO) {
        //댓글 등록하기

        Optional<Task> result = taskRepository.getTaskByTid(Long.parseLong(taskReplyDTO.getTid()));

        if(!result.isEmpty()){
            Task task = result.get();

            TaskReply taskReply = TaskReply.builder()
                    .task(task)
                    .uid(taskReplyDTO.getUid())
                    .replyContent(taskReplyDTO.getReplyContent())
                    .build();

            taskReplyRepository.save(taskReply);
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public List<TaskReplyDTO> getTaskRepltList(String tid) {
       //댓글 리스트 받아오기

        Optional<Task> result = taskRepository.getTaskByTid(Long.parseLong(tid));

        if(!result.isEmpty()) {
            Task task = result.get();

            List<TaskReply> taskReplyList = taskReplyRepository.getTaskReplyByTid(task);

            if(taskReplyList==null){
                return  null;
            }else {

                List<TaskReplyDTO> taskDTOList= taskReplyList.stream().map(taskReply -> {

                    TaskReplyDTO dto = TaskReplyDTO.builder()
                            .rid(taskReply.getRid().toString())
                            .uid(taskReply.getUid())
                            .replyContent(taskReply.getReplyContent())
                            .regDate(taskReply.getRegDate())
                            .modDate(taskReply.getModDate())
                            .build();
                    return dto;

                }).collect(Collectors.toList());

                return taskDTOList;
            }

        }else {
            return null;
        }
    }

    @Override
    public int registerReReply(TaskReReplyDTO reReplyDTO) {
        //대댓글을 등록하는 메서드

        Optional<TaskReply> taskReplyResult = taskReplyRepository.getTaskReplyByRid(Long.parseLong(reReplyDTO.getRid()));

        if(!taskReplyResult.isEmpty()){

            TaskReply taskReply = taskReplyResult.get();

            TaskReReply taskReReply = TaskReReply.builder()
                    .replyText(reReplyDTO.getReplyText())
                    .taskReply(taskReply)
                    .uid(reReplyDTO.getUid())
                    .build();

            taskReReplyRepository.save(taskReReply);
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public List<TaskReReplyDTO> getTaskReReplyList(String rid) {
        //대댓글 리스트 가져오는 메서드

        Optional<TaskReply> result = taskReplyRepository.getTaskReplyByRid(Long.parseLong(rid));

        if(!result.isEmpty()){

            TaskReply taskReply = result.get();

            List<TaskReReply> taskReReplyList = taskReReplyRepository.getTaskReRepliesByRid(taskReply);

            List<TaskReReplyDTO> taskReplyDTOList = taskReReplyList.stream().map(taskReReply -> {

                TaskReReplyDTO taskReReplyDTO = TaskReReplyDTO.builder()
                        .rrid(taskReReply.getRrid().toString())
                        .uid(taskReReply.getUid())
                        .replyText(taskReReply.getReplyText())
                        .regDate(taskReReply.getRegDate())
                        .modDate(taskReReply.getModDate())
                        .build();
                return  taskReReplyDTO;

            }).collect(Collectors.toList());
            return taskReplyDTOList;
        }else {
            return null;
        }
    }
}
