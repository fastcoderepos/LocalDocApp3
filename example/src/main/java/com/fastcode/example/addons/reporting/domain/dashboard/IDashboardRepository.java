package com.fastcode.example.addons.reporting.domain.dashboard;

import java.time.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository("dashboardRepository")
public interface IDashboardRepository
    extends
        JpaRepository<DashboardEntity, Long>, QuerydslPredicateExecutor<DashboardEntity>, IDashboardRepositoryCustom {
    @Query("select r from DashboardEntity r where r.id = ?1 and r.user.id = ?2")
    DashboardEntity findByDashboardIdAndUserId(Long dashboardId, Long userId);

    @Query("select r from DashboardEntity r where r.user.id = ?1")
    List<DashboardEntity> findByUserId(Long userId);
}
