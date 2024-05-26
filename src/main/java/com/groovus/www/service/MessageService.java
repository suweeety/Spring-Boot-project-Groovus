package com.groovus.www.service;

import com.groovus.www.dto.MessageDTO;
import com.groovus.www.dto.ProjectPageRequestDTO;
import com.groovus.www.dto.ProjectPageResponseDTO;

public interface MessageService {

    public ProjectPageResponseDTO<MessageDTO> getMessageListWithPaging(ProjectPageRequestDTO pageRequestDTO , Long pid,Long mid);

     void sendMessage(MessageDTO messageDTO,Long pid);

     MessageDTO readMessage(Long lid , Long mid);

     Long countMessage(Long pid, Long mid);

     //쪽지를 삭제하는 메섣
     int deleteMessage(Long lid);
}
