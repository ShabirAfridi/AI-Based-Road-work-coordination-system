package com.UNI_FYP.AI_Based_Road_Construction.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "road_segments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoadSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This name is the KEY used in conflict detection
    // e.g. "Murree Road G-9" — must be unique and consistent
    @Column(nullable = false, unique = true, length = 200)
    private String name;

    @Column(length = 150)
    private String area;

    @Column(nullable = false, length = 100)
    @Builder.Default
    private String city = "Islamabad";

    // Optional — used by Leaflet.js to place the map marker
    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // ── Relationships ──────────────────────────────────────
    @OneToMany(mappedBy = "roadSegment", fetch = FetchType.LAZY)
    @Builder.Default
    private List<WorkRequest> workRequests = new ArrayList<>();
}