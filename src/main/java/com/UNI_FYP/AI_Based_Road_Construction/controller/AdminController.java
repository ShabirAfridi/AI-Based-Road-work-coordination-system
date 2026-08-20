package com.UNI_FYP.AI_Based_Road_Construction.controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.UNI_FYP.AI_Based_Road_Construction.dto.*;
import com.UNI_FYP.AI_Based_Road_Construction.entity.enums.ComplaintStatus;
import com.UNI_FYP.AI_Based_Road_Construction.entity.User;
import com.UNI_FYP.AI_Based_Road_Construction.service.AdminService;
import com.UNI_FYP.AI_Based_Road_Construction.service.CitizenComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final CitizenComplaintService complaintService;


    // ── WORK REQUEST ENDPOINTS ─────────────────────────────────

    // GET /api/admin/requests
    // Returns all work requests — admin sees everything
    @GetMapping("/requests")
    public ResponseEntity<ApiResponse<List<WorkRequestResponse>>> getAllRequests() {
        return ResponseEntity.ok(
                ApiResponse.success("All work requests retrieved",
                        adminService.getAllRequests()));
    }

    // PUT /api/admin/requests/{id}/approve
    // Body: { "adminRemarks": "Approved for next week" }
    @PutMapping("/requests/{id}/approve")
    public ResponseEntity<ApiResponse<WorkRequestResponse>> approve(
            @PathVariable Long id,
            @RequestBody AdminDecisionRequest decision,
            @AuthenticationPrincipal User adminUser
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("Request approved successfully",
                        adminService.approveRequest(id, decision, adminUser)));
    }

    // PUT /api/admin/requests/{id}/reject
    // Body: { "adminRemarks": "Conflicting with road maintenance schedule" }
    // adminRemarks is mandatory for rejection
    @PutMapping("/requests/{id}/reject")
    public ResponseEntity<ApiResponse<WorkRequestResponse>> reject(
            @PathVariable Long id,
            @RequestBody AdminDecisionRequest decision,
            @AuthenticationPrincipal User adminUser
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("Request rejected successfully",
                        adminService.rejectRequest(id, decision, adminUser)));
    }

    // PUT /api/admin/requests/{id}/start
    // Marks an APPROVED request as IN_PROGRESS
    @PutMapping("/requests/{id}/start")
    public ResponseEntity<ApiResponse<WorkRequestResponse>> markInProgress(
            @PathVariable Long id,
            @AuthenticationPrincipal User adminUser
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("Request marked as IN_PROGRESS",
                        adminService.markInProgress(id, adminUser)));
    }

    // PUT /api/admin/requests/{id}/complete
    // Marks an IN_PROGRESS request as COMPLETED
    @PutMapping("/requests/{id}/complete")
    public ResponseEntity<ApiResponse<WorkRequestResponse>> markCompleted(
            @PathVariable Long id,
            @AuthenticationPrincipal User adminUser
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("Request marked as COMPLETED",
                        adminService.markCompleted(id, adminUser)));
    }

    // ── CONFLICT ENDPOINTS ─────────────────────────────────────

    // GET /api/admin/conflicts
    // Returns all conflicts (all resolution statuses)
    @GetMapping("/conflicts")
    public ResponseEntity<ApiResponse<List<ConflictResponse>>> getAllConflicts() {
        return ResponseEntity.ok(
                ApiResponse.success("All conflicts retrieved",
                        adminService.getAllConflicts()));
    }

    // GET /api/admin/conflicts/pending
    // Returns only PENDING conflicts needing attention
    // This is the main view on the admin dashboard
    @GetMapping("/conflicts/pending")
    public ResponseEntity<ApiResponse<List<ConflictResponse>>> getPendingConflicts() {
        return ResponseEntity.ok(
                ApiResponse.success("Pending conflicts retrieved",
                        adminService.getPendingConflicts()));
    }

    // PUT /api/admin/conflicts/{id}/resolve
    // Body examples:
    //
    // MERGED:
    // { "resolution": "MERGED", "adminRemarks": "Both depts will dig together" }
    //
    // ONE_REJECTED:
    // { "resolution": "ONE_REJECTED", "rejectRequestId": 3,
    //   "adminRemarks": "PTCL must wait until WAPDA completes" }
    //
    // RESCHEDULED:
    // { "resolution": "RESCHEDULED",
    //   "adminRemarks": "Both depts must submit new dates" }
    @PutMapping("/conflicts/{id}/resolve")
    public ResponseEntity<ApiResponse<ConflictResponse>> resolveConflict(
            @PathVariable Long id,
            @RequestBody AdminDecisionRequest decision,
            @AuthenticationPrincipal User adminUser
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("Conflict resolved successfully",
                        adminService.resolveConflict(id, decision, adminUser)));
    }
    // api/admin/complaints
@GetMapping("/complaints")
    public ResponseEntity<ApiResponse<List<ComplaintResponse>>> getAllComplaints() {
        return ResponseEntity.ok(
                ApiResponse.success("All Complaints retrieved",complaintService.getAll()));
       }
       @GetMapping("/complaints/submitted")
       public ResponseEntity<ApiResponse<List<ComplaintResponse>>> getSubmittedComplaints(){
        return ResponseEntity.ok(
                ApiResponse.success("submitted complaints retrieved",
                        complaintService.getByStatus(ComplaintStatus.SUBMITTED)));

    }
    // /api/admin/complaints/{id}/review
    @PutMapping("/complaints/{id}/review")
    public ResponseEntity<ApiResponse<ComplaintResponse>> markUnderReview(@PathVariable Long id){
        return ResponseEntity.ok(
                ApiResponse.success("Complaint marked as Under Review ",
                        complaintService.markUnderReview(id)));
    }
    //api/admin/complaints/resolve
    @PutMapping("complaints/{id}/resolve")
    public ResponseEntity<ApiResponse<List<ComplaintResponse>>> resolveComplaints(
            @PathVariable Long id,
            @RequestBody ComplaintResolveRequest body
    ){
        return ResponseEntity.ok(
                ApiResponse.success("Complaint resolved ",
                        Collections.singletonList(complaintService.resolve(id, body.getAdminResponse(),
                                body.isDismiss())))
        );
    }
}
