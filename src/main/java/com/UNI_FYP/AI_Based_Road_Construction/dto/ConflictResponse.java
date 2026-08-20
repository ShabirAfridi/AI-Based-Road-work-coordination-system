package com.UNI_FYP.AI_Based_Road_Construction.dto;

import com.UNI_FYP.AI_Based_Road_Construction.entity.enums.ConflictResolution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConflictResponse {
    private Long id;
    private ConflictResolution resolutionStatus;
    private String adminRemarks;
    private LocalDateTime detectedAt;
    private LocalDateTime resolvedAt;

    // Request A details
    private Long   requestAId;
    private String requestADepartment;
    private String requestARoadSegment;
    private String requestAStartDate;
    private String requestAEndDate;

    // Request B details
    private Long   requestBId;
    private String requestBDepartment;
    private String requestBRoadSegment;
    private String requestBStartDate;
    private String requestBEndDate;
}
