package com.groovus.www.repository.search;

import com.groovus.www.entity.DriveBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DriveBoardSearch {

    Page<DriveBoard> search1(Pageable pageable);

    Page<DriveBoard> searchAll(String[] types, String keyword, Pageable pageable);


}
