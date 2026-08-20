package com.UNI_FYP.AI_Based_Road_Construction.controller;

import com.UNI_FYP.AI_Based_Road_Construction.dto.ApiResponse;
import com.UNI_FYP.AI_Based_Road_Construction.dto.WorkRequestRequest;
import com.UNI_FYP.AI_Based_Road_Construction.dto.WorkRequestResponse;
import com.UNI_FYP.AI_Based_Road_Construction.entity.User;
import com.UNI_FYP.AI_Based_Road_Construction.service.WorkRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-requests")
@RequiredArgsConstructor
public class Workrequestcontroller {
    private final WorkRequestService workRequestService;

    // POST /api/work-requests
    // Only DEPARTMENT_USER can submit a work request
    // @AuthenticationPrincipal injects the logged-in User automatically
    // from SecurityContextHolder — no need to query DB again
    @PostMapping
    @PreAuthorize("hasRole('DEPARTMENT_USER')")
    public ResponseEntity<ApiResponse<WorkRequestResponse>> submit(
            @Valid @RequestBody WorkRequestRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        WorkRequestResponse response = workRequestService.submit(request, currentUser);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Work request submitted successfully", response));
    }

    // GET /api/work-requests
    // ADMIN sees all, DEPARTMENT_USER sees only their own
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DEPARTMENT_USER')")
    public ResponseEntity<ApiResponse<List<WorkRequestResponse>>> getAll(
            @AuthenticationPrincipal User currentUser
    ) {
        List<WorkRequestResponse> responses = workRequestService.getAll(currentUser);
        return ResponseEntity.ok(
                ApiResponse.success("Work requests retrieved", responses));
    }

    // GET /api/work-requests/{id}
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEPARTMENT_USER')")
    public ResponseEntity<ApiResponse<WorkRequestResponse>> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser
    ) {
        WorkRequestResponse response = workRequestService.getById(id, currentUser);
        return ResponseEntity.ok(
                ApiResponse.success("Work request retrieved", response));
    }

    // PUT /api/work-requests/{id}
    // Only allowed if status is PENDING or CONFLICT_FLAGGED
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEPARTMENT_USER')")
    public ResponseEntity<ApiResponse<WorkRequestResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody WorkRequestRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        WorkRequestResponse response = workRequestService.update(id, request, currentUser);
        return ResponseEntity.ok(
                ApiResponse.success("Work request updated successfully", response));
    }

    // DELETE /api/work-requests/{id}
    // Sets status to CANCELLED — does not delete from DB
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEPARTMENT_USER')")
    public ResponseEntity<ApiResponse<Void>> cancel(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser
    ) {
        workRequestService.cancel(id, currentUser);
        return ResponseEntity.ok(
                ApiResponse.success("Work request cancelled successfully"));
    }

    // GET /api/work-requests/active
    // Public-ish — returns APPROVED + IN_PROGRESS requests for the map
    // Called by Leaflet.js to place markers on the map
    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEPARTMENT_USER', 'CITIZEN')")
    public ResponseEntity<ApiResponse<List<WorkRequestResponse>>> getActive() {
        List<WorkRequestResponse> responses = workRequestService.getActive();
        return ResponseEntity.ok(
                ApiResponse.success("Active work requests retrieved", responses));
    }
}