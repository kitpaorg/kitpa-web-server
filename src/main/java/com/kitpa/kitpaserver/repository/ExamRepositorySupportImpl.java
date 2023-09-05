package com.kitpa.kitpaserver.repository;

import com.kitpa.kitpaserver.entity.Exam;
import com.kitpa.kitpaserver.entity.QExam;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.kitpa.kitpaserver.entity.QExam.*;

@RequiredArgsConstructor
//@Repository
public class ExamRepositorySupportImpl{
    private final JPAQueryFactory queryFactory;

//    public Page<Exam> findPageByCanReceipt(Pageable pageable, LocalDateTime now){
//        List<Exam> fetch = queryFactory.selectFrom(exam)
//                .where(exam.receiptStartDate.loe(now), exam.receiptEndDate.goe(now))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//        return PageableExecutionUtils.getPage(fetch, pageable, () -> getCountByCanReceipt(now));
//    }

//    public Long getCountByCanReceipt(LocalDateTime now){
//        return (long) queryFactory.selectFrom(exam)
//                .where(exam.receiptStartDate.loe(now), exam.receiptEndDate.goe(now))
//                .fetch().size();
//    }
}
