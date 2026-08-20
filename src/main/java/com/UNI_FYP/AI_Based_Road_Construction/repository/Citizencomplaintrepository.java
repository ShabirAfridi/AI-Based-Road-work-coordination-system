package com.UNI_FYP.AI_Based_Road_Construction.repository;
import com.UNI_FYP.AI_Based_Road_Construction.entity.CitizenComplaint;
import com.UNI_FYP.AI_Based_Road_Construction.entity.enums.ComplaintCategory;
import com.UNI_FYP.AI_Based_Road_Construction.entity.enums.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface Citizencomplaintrepository extends JpaRepository<CitizenComplaint,Long> {

    // Get all complaints with a specific status
    // Used in: Admin dashboard — "show all SUBMITTED complaints"
    List<CitizenComplaint> findByStatus(ComplaintStatus status);

    // Get all complaints for a specific road segment
    // Used in: Road segment detail view — "show complaints on this road"
    List<CitizenComplaint> findByRoadSegmentId(Long roadSegmentId);

    // Get all complaints by category
    // Used in: Admin analytics — "how many ILLEGAL_DIGGING complaints?"
    List<CitizenComplaint> findByCategory(ComplaintCategory category);

    // Get all complaints submitted by a specific user
    // Used in: Citizen profile — "show my complaints"
    List<CitizenComplaint> findByReportedById(Long userId);

    // Count complaints by status — used in admin dashboard stats card
    long countByStatus(ComplaintStatus status);
}
