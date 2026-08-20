package com.UNI_FYP.AI_Based_Road_Construction.controller;

import com.UNI_FYP.AI_Based_Road_Construction.dto.ApiResponse;
import com.UNI_FYP.AI_Based_Road_Construction.dto.ComplaintRequest;
import com.UNI_FYP.AI_Based_Road_Construction.dto.ComplaintResponse;
import com.UNI_FYP.AI_Based_Road_Construction.entity.User;
import com.UNI_FYP.AI_Based_Road_Construction.service.CitizenComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CitizenController {
    private final CitizenComplaintService complaintService;
@PostMapping("/api/public/complaints")
    public ResponseEntity<ApiResponse<ComplaintResponse>> submitComplaint(
            @Valid @RequestBody ComplaintRequest request,
            @AuthenticationPrincipal User currentUser
            ){
        ComplaintResponse response = complaintService.submit(request,currentUser);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Complaint submitted successfully",response));

    }
    @GetMapping("/api/complaints/mine")
    @PreAuthorize("hasAnyRole('CITIZEN', 'ADMIN', 'DEPARTMENT_USER')")
    public ResponseEntity<ApiResponse<List<ComplaintResponse>>> getMyComplaints(
            @AuthenticationPrincipal User currentuser
    ){
    return ResponseEntity.ok(
            ApiResponse.success("Your Complaint retrieved",
                    complaintService.getMyComplaints(currentuser)));
    }
}
