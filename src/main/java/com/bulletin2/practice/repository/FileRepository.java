package com.bulletin2.practice.repository;

import com.bulletin2.practice.domain.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
