package com.groovus.www.repository;

import com.groovus.www.entity.Bin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinRepository extends JpaRepository<Bin, Long> {
}
