package com.groovus.www.service;

import com.groovus.www.entity.Drive;
import com.groovus.www.repository.BinRepository;
import com.groovus.www.repository.DriveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class BinServiceImpl implements BinService{

    private final DriveRepository driveRepository;
    private final BinRepository binRepository;

    @Override
    public void saveBin(Drive drive) {

    }
}
