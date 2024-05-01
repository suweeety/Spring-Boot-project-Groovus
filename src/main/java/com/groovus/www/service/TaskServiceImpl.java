package com.groovus.www.service;

import com.groovus.www.dto.ProjectPageRequestDTO;
import com.groovus.www.dto.ProjectPageResponseDTO;
import com.groovus.www.dto.TaskDTO;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Project;
import com.groovus.www.entity.Task;
import com.groovus.www.repository.MemberRepository;
import com.groovus.www.repository.ProjectRepository;
import com.groovus.www.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    private  final MemberRepository memberRepository;

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
}
