package com.kitpa.kitpaserver.repository;

import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.entity.AccountExam;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.kitpa.kitpaserver.entity.QAccountExam.*;

@RequiredArgsConstructor
@Repository
public class AccountExamRepositorySupportImpl implements AccountExamRepositorySupport{
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<AccountExam> findByCanCancellation(Pageable pageable, Account account) {
        LocalDateTime now = LocalDateTime.now();
        List<AccountExam> fetch = queryFactory.selectFrom(accountExam)
                .join(accountExam.exam)
                .fetchJoin()
                .join(accountExam.account)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(accountExam.account.eq(account), accountExam.exam.receiptEndDate.gt(now))
                .fetch();
        return PageableExecutionUtils.getPage(fetch, pageable, ()->getCountByCanCancellation(now, account));
    }

    public Long getCountByCanCancellation(LocalDateTime now, Account account) {
        return (long) queryFactory.selectFrom(accountExam)
                .join(accountExam.exam)
                .join(accountExam.account)
                .where(accountExam.account.eq(account), accountExam.exam.receiptEndDate.gt(now))
                .fetch().size();
    }
}
