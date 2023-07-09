package com.kitpa.kitpaserver.repository;

import com.kitpa.kitpaserver.entity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ExamRepositorySupport {
    Page<Exam> findPageByCanReceipt(Pageable pageable, LocalDateTime now);
}
