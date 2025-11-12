package de.szut.lf8_starter.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    @Query("SELECT COUNT(e) FROM EmployeeEntity e WHERE e.responsibleEmployeeId = :employeeId " +
            "AND e.project.id != :projectId " +
            "AND ((e.startDate <= :endDate AND e.endDate >= :startDate))")
    long countOverlappingAssignments(@Param("employeeId") Long employeeId,
                                     @Param("projectId") Long projectId,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    void deleteByEmployeeIdAndProjectId(Long employeeId, Long projectId);
}