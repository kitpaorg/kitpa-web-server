package com.kitpa.kitpaserver.repository;

import com.kitpa.kitpaserver.entity.Exam;
import com.kitpa.kitpaserver.entity.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByIdIn(List<Long> id);
    Page<Problem> findPageBy(Pageable pageable);
    Page<Problem> findPageByExamIsNull(Pageable pageable);
    Optional<Problem> findProblemByExamAndProblemNumber(Exam exam, Integer problemNumber);
}
