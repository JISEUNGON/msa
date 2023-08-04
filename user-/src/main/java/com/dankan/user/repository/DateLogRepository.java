package com.dankan.user.repository;

import com.dankan.user.domain.DateLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateLogRepository extends JpaRepository<DateLog, Long> {
}
