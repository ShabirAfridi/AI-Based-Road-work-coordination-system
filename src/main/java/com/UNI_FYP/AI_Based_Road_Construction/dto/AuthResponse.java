package com.UNI_FYP.AI_Based_Road_Construction.dto;

import com.UNI_FYP.AI_Based_Road_Construction.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    // The JWT token — frontend stores this and sends it on every request
    private String token;

    // Extra info so the frontend knows which dashboard to show
    private String email;
    private String fullName;
    private UserRole role;
    private Long departmentId; // null if ADMIN or CITIZEN
}