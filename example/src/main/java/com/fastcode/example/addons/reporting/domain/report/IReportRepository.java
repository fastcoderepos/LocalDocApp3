package com.fastcode.example.addons.reporting.domain.report;

import java.time.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository("reportRepository")
public interface IReportRepository
    extends JpaRepository<ReportEntity, Long>, QuerydslPredicateExecutor<ReportEntity>, IReportRepositoryCustom {
    @Query("select r from ReportEntity r where r.id = ?1 and r.user.id = ?2")
    ReportEntity findByReportIdAndUserId(Long reportId, Long userId);

    @Query("select r from ReportEntity r where r.user.id = ?1")
    List<ReportEntity> findByUserId(Long userId);
}
