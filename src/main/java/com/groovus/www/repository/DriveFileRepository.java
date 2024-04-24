package com.groovus.www.repository;

import com.groovus.www.entity.DriveFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriveFileRepository extends JpaRepository<DriveFile, Long> {
}
