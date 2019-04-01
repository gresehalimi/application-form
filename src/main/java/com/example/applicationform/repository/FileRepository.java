package com.example.applicationform.repository;

import com.example.applicationform.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<File, Long> {
}
