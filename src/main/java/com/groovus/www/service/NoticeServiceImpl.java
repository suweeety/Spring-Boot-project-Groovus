package com.groovus.www.service;

import com.groovus.www.dto.notice.NoticeDTO;
import com.groovus.www.repository.NoticeRepository;
import com.groovus.www.repository.ProjectRepository;
import groovy.util.logging.Slf4j;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public Long register(NoticeDTO noticeDTO) {
        return null;
    }
}
