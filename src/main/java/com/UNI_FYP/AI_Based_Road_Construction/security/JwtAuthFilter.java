package com.UNI_FYP.AI_Based_Road_Construction.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component
@RequiredArgsConstructor
// OncePerRequestFilter = Spring guarantees this runs exactly ONCE per request
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // ── Step 1: Read the Authorization header ──────────────────
        // Every protected request must have this header:
        // Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIi...
        final String authHeader = request.getHeader("Authorization");

        // If header is missing or doesn't start with "Bearer " → skip filter
        // This request will be rejected later by SecurityConfig
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // ── Step 2: Extract the token from the header ───────────────
        // "Bearer eyJhbGci..." → we skip the first 7 chars ("Bearer ")
        final String jwt = authHeader.substring(7);

        // ── Step 3: Extract the email from the token ────────────────
        final String userEmail = jwtService.extractUsername(jwt);

        // ── Step 4: Load the user only if not already authenticated ─
        // SecurityContextHolder.getContext().getAuthentication() == null
        // means this request has not been authenticated yet this session
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load user from DB by email — calls UserDetailsService.loadUserByUsername()
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // ── Step 5: Validate the token ──────────────────────────
            // Checks: does email match? is token expired?
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // ── Step 6: Create authentication token ────────────
                // UsernamePasswordAuthenticationToken tells Spring Security
                // "this user is authenticated, here are their authorities"
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,                        // credentials = null (already verified)
                                userDetails.getAuthorities() // ROLE_ADMIN, ROLE_DEPARTMENT_USER etc.
                        );

                // Attach request details (IP address, session info) to the token
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // ── Step 7: Set authentication in SecurityContext ───
                // After this line Spring Security considers this request
                // fully authenticated — controllers can use @AuthenticationPrincipal
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // ── Step 8: Pass request to the next filter in the chain ───
        // If authentication was set above → request reaches your controller
        // If authentication was NOT set → SecurityConfig rejects it with 403
        filterChain.doFilter(request, response);
    }
}