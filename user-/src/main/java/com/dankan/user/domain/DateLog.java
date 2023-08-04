package com.dankan.user.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "date_log")
public class DateLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "int")
    private Long id;

    @Column(name = "created_at", nullable = false, columnDefinition = "date")
    private LocalDateTime createdAt;

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint")
    private Long userId;

    public static DateLog of(Long userId) {
        return DateLog.builder()
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
