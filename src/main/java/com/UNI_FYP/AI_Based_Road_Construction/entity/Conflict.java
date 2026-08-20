package com.UNI_FYP.AI_Based_Road_Construction.entity;
import com.UNI_FYP.AI_Based_Road_Construction.entity.enums.ConflictResolution;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "conflicts",
        uniqueConstraints = {
                // prevent duplicate conflict pairs — same pair can't be saved twice
                @UniqueConstraint(columnNames = {"request_a_id", "request_b_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conflict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // First conflicting request
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_a_id", nullable = false)
    private WorkRequest requestA;

    // Second conflicting request
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_b_id", nullable = false)
    private WorkRequest requestB;

    // PENDING until admin takes action
    @Enumerated(EnumType.STRING)
    @Column(name = "resolution_status", nullable = false)
    @Builder.Default
    private ConflictResolution resolutionStatus = ConflictResolution.PENDING;

    @Column(name = "admin_remarks", columnDefinition = "TEXT")
    private String adminRemarks;

    // Which admin resolved this conflict
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolved_by")
    private User resolvedBy;

    @CreationTimestamp
    @Column(name = "detected_at", updatable = false)
    private LocalDateTime detectedAt;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;
}