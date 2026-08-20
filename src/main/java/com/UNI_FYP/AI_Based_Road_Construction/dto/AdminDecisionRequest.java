package com.UNI_FYP.AI_Based_Road_Construction.dto;

import com.UNI_FYP.AI_Based_Road_Construction.entity.enums.ConflictResolution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDecisionRequest {
    // Admin remarks — reason for approval/rejection
    private String adminRemarks;

    // Only needed when resolving a conflict with ONE_REJECTED
    // Tells the system which of the two requests to reject
    private Long rejectRequestId;

    // Only needed when resolving a conflict
    private ConflictResolution resolution;
}
