package com.dankan.userservice.repository;

import com.dankan.userservice.domain.DateLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateLogRepository extends JpaRepository<DateLog, Long> {
}
