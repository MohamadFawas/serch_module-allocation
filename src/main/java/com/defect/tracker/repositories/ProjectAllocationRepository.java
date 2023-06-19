package com.defect.tracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.defect.tracker.entities.ProjectAllocation;

public interface ProjectAllocationRepository
    extends JpaRepository<ProjectAllocation, Long>, QuerydslPredicateExecutor<ProjectAllocation> {
  boolean existsByEmployeeId(Long employeeid);

  boolean existsByRoleId(Long roleId);

  List<ProjectAllocation> findByProjectId(Long projectId);

  List<ProjectAllocation> findByEmployeeId(Long employeeId);

  List<ProjectAllocation> findByRoleId(Long roleId);

  boolean existsByProjectId(Long id);

  boolean existsByEmployeeIdAndProjectId(Long employeeId, Long projectId);

  boolean existsByEmployeeIdAndProjectIdAndIdNot(Long employeeId, Long projectId, Long Id);
}
