package com.groovus.www.repository.search;

import com.groovus.www.dto.DriveAllDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.groovus.www.entity.Drive;

public interface DriveSearch {

    Page<Drive> search1(Pageable pageable);

    Page<Drive> searchAll(String[] types, String keyword, Pageable pageable);

//    Page<DriveAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable);
}