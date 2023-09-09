package com.kitpa.kitpaserver.repository;

import com.kitpa.kitpaserver.entity.AccountProblem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountProblemRepository extends JpaRepository<AccountProblem, Long> {
    @EntityGraph(attributePaths = "account")
    @Query("select ap from AccountProblem ap join fetch ap.account a where a.userId=:userId and ap.problemNumber=:problemNumber")
    Optional<AccountProblem> findByUserIdAndProblemNumber(String userId, Integer problemNumber);
}
