package com.groovus.www.service;

import com.groovus.www.dto.MessageDTO;
import com.groovus.www.dto.ProjectPageRequestDTO;
import com.groovus.www.dto.ProjectPageResponseDTO;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Message;
import com.groovus.www.entity.Project;
import com.groovus.www.repository.MemberRepository;
import com.groovus.www.repository.MessageRepository;
import com.groovus.www.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    @Override
    public ProjectPageResponseDTO<MessageDTO> getMessageListWithPaging(ProjectPageRequestDTO pageRequestDTO, Long pid, Long mid) {

        String [] types= pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("lid");

        Page<MessageDTO> result = messageRepository.searchAll(types,keyword,pageable,pid,mid);

        List<MessageDTO> messageDTOList = result.stream().toList();

        return ProjectPageResponseDTO.<MessageDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(messageDTOList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public void sendMessage(MessageDTO messageDTO, Long pid) {
        //메세지 보내기

        Optional<Project> projectResult = projectRepository.findById(pid);
        Project project = projectResult.get();

        Optional<Member> receiverResult = memberRepository.findByUid(messageDTO.getReceiver());
        Member receiver = receiverResult.get();

        Optional<Member> senderResult =memberRepository.findByUid(messageDTO.getSender());
        Member sender = senderResult.get();

        LocalDateTime now = LocalDateTime.now();

        Message message = Message.builder()
                .content(messageDTO.getContent())
                .project(project)
                .receiver(receiver)
                .title(messageDTO.getTitle())
                .sender(sender)
                .sendDate(now)
                .build();

        messageRepository.save(message);
    }
}
