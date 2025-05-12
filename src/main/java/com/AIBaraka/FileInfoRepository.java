package com.AIBaraka;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo, Integer> {

    // Method to fetch files by a specific user ID
    List<FileInfo> findByUser_UserId(Integer userId);

    // Method to count files by status, grouped by status
    @Query("SELECT f.status, COUNT(f) FROM FileInfo f GROUP BY f.status")
    List<Object[]> countFilesByStatusGrouped();

    // Count files by specific status. Returning the count as a single Long.
    @Query("SELECT COUNT(f) FROM FileInfo f WHERE f.status = :status")
    Long countFilesByStatus(@Param("status") String status);

    // Alternatively, you can return a list of counts for all statuses
    @Query("SELECT f.status, COUNT(f) FROM FileInfo f GROUP BY f.status")
    List<Object[]> countFilesByAllStatuses();
}
