package com.defect.tracker.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.entities.ProjectAllocation;
import com.defect.tracker.response.dto.ProjectAllocationResponse;
import com.defect.tracker.resquest.dto.ProjectAllocationRequest;
import com.defect.tracker.search.response.ProjectAllocationSearch;

public interface ProjectAllocationService {
  public boolean existsByEmployee(Long employeeid);

  public boolean existsByRole(Long roleId);

  public void saveProjectAllocation(ProjectAllocationRequest allocationRequest);

  public List<ProjectAllocationResponse> getAllProjectAllocation();

  public ProjectAllocationResponse getProjectAllocationById(Long id);

  public boolean existsByProjectAllocation(Long id);

  public List<ProjectAllocation> getAllProjectAllocationByProjectId(Long id);

  public List<ProjectAllocation> getAllProjectAllocationByEmployeeId(Long id);

  public List<ProjectAllocation> getAllProjectAllocationByRoleId(Long id);

  public boolean existsByProjectId(Long id);

  public boolean existsByEmployeeId(Long id);

  public boolean existsByRoleId(Long id);

  boolean existsByEmployeeAndProject(Long employeeId, Long projectId);

  public boolean existByProjectAllocation(Long id);

  public boolean isUpdatedProjectAllocationEmployeeAndProject(Long EmployeeId, Long ProjectId,
      Long ProjectAllocationId);

  public void deleteProjectAllocation(Long id);

  List<ProjectAllocationResponse> multiSearchProjectAllocation(Pageable pageable,
      Pagination pagination, ProjectAllocationSearch projectAllocationSearch);
}
