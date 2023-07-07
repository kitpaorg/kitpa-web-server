package com.kitpa.kitpaserver.repository;

import com.kitpa.kitpaserver.dto.ExamDto;
import com.kitpa.kitpaserver.entity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    Page<Exam> findPageBy(PageRequest pageRequest);

    @EntityGraph(attributePaths = {"problems"})
    Optional<Exam> findWithRelationById(Long id);
}
