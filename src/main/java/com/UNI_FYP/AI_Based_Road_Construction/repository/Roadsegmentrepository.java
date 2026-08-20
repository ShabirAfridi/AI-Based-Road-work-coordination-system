package com.UNI_FYP.AI_Based_Road_Construction.repository;
import com.UNI_FYP.AI_Based_Road_Construction.entity.RoadSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
@Repository
public interface Roadsegmentrepository extends JpaRepository<RoadSegment,Long> {

    // Find by exact name — used when submitting a work request
    // Department selects road segment by name from a dropdown
    Optional<RoadSegment> findByName(String name);

    // Check if a road segment name already exists
    // Used when admin creates a new road segment
    boolean existsByName(String name);

    // Get all active road segments
    // Used to populate the dropdown in the work request form
    List<RoadSegment> findByIsActiveTrue();

    // Search road segments by city
    // Used in citizen portal — "show roads in Islamabad"
    List<RoadSegment> findByCityIgnoreCase(String city);

    // Search road segments by name containing a keyword
    // Used in search bar — type "Murree" → shows all Murree Road segments
    List<RoadSegment> findByNameContainingIgnoreCase(String keyword);
}
