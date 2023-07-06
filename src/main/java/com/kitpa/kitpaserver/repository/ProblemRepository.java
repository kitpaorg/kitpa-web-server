package com.kitpa.kitpaserver.repository;

import com.kitpa.kitpaserver.entity.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Page<Problem> findPageBy(Pageable pageable);
}
