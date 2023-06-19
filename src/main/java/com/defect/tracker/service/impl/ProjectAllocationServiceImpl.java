package com.defect.tracker.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.entities.Employee;
import com.defect.tracker.entities.Project;
import com.defect.tracker.entities.ProjectAllocation;
import com.defect.tracker.entities.QEmployee;
import com.defect.tracker.entities.QProject;
import com.defect.tracker.entities.QRole;
import com.defect.tracker.entities.Role;
import com.defect.tracker.repositories.ProjectAllocationRepository;
import com.defect.tracker.response.dto.ProjectAllocationResponse;
import com.defect.tracker.resquest.dto.ProjectAllocationRequest;
import com.defect.tracker.search.response.ProjectAllocationSearch;
import com.defect.tracker.service.ProjectAllocationService;
import com.defect.tracker.utils.Utils;
import com.querydsl.core.BooleanBuilder;

@Service
public class ProjectAllocationServiceImpl implements ProjectAllocationService {
  @Autowired
  private ProjectAllocationRepository projectAllocationRepository;

  @Override
  public boolean existsByEmployee(Long employeeid) {
    return projectAllocationRepository.existsByEmployeeId(employeeid);
  }

  @Override
  public boolean existsByRole(Long roleId) {
    return projectAllocationRepository.existsByRoleId(roleId);
  }

  @Transactional
  public void saveProjectAllocation(ProjectAllocationRequest projectAllocationRequest) {
    ProjectAllocation projectAllocation = new ProjectAllocation();
    BeanUtils.copyProperties(projectAllocationRequest, projectAllocation);
    Project project = new Project();
    project.setId(projectAllocationRequest.getProject_id());
    projectAllocation.setProject(project);
    Role role = new Role();
    role.setId(projectAllocationRequest.getRole_id());
    projectAllocation.setRole(role);
    Employee employee = new Employee();
    employee.setId(projectAllocationRequest.getEmployee_id());
    projectAllocation.setEmployee(employee);
    projectAllocationRepository.save(projectAllocation);
  }

  @Transactional
  public List<ProjectAllocationResponse> getAllProjectAllocation() {
    List<ProjectAllocationResponse> projectAllocationResponses = new ArrayList<>();
    List<ProjectAllocation> projectAllocations = projectAllocationRepository.findAll();
    for (ProjectAllocation projectAllocation : projectAllocations) {
      ProjectAllocationResponse projectAllocationResponse = new ProjectAllocationResponse();
      BeanUtils.copyProperties(projectAllocation, projectAllocationResponse);
      projectAllocationResponse.setEmployee_id(projectAllocation.getEmployee().getId());
      projectAllocationResponse.setEmployeeName(projectAllocation.getEmployee().getFirstName());
      projectAllocationResponse.setProject_id(projectAllocation.getProject().getId());
      projectAllocationResponse.setProjectName(projectAllocation.getProject().getProjectName());
      projectAllocationResponse.setRole_id(projectAllocation.getRole().getId());
      projectAllocationResponse.setRoleName(projectAllocation.getRole().getName());
      projectAllocationResponses.add(projectAllocationResponse);
    }
    return projectAllocationResponses;
  }

  @Transactional
  public ProjectAllocationResponse getProjectAllocationById(Long id) {
    ProjectAllocation projectAllocation = projectAllocationRepository.findById(id).get();
    ProjectAllocationResponse projectAllocationResponse = new ProjectAllocationResponse();
    BeanUtils.copyProperties(projectAllocation, projectAllocationResponse);
    projectAllocationResponse.setEmployee_id(projectAllocation.getEmployee().getId());
    projectAllocationResponse.setEmployeeName(projectAllocation.getEmployee().getFirstName());
    projectAllocationResponse.setProject_id(projectAllocation.getProject().getId());
    projectAllocationResponse.setProjectName(projectAllocation.getProject().getProjectName());
    projectAllocationResponse.setRole_id(projectAllocation.getRole().getId());
    projectAllocationResponse.setRoleName(projectAllocation.getRole().getName());
    return projectAllocationResponse;
  }

  @Override
  public boolean existsByProjectAllocation(Long id) {
    return projectAllocationRepository.existsById(id);
  }

  @Override
  public boolean existsByEmployeeId(Long id) {
    return projectAllocationRepository.existsByEmployeeId(id);
  }

  @Override
  public boolean existsByRoleId(Long id) {
    return projectAllocationRepository.existsByRoleId(id);
  }

  @Override
  public boolean existsByEmployeeAndProject(Long employeeId, Long projectId) {
    return projectAllocationRepository.existsByEmployeeIdAndProjectId(employeeId, projectId);
  }

  @Override
  public List<ProjectAllocation> getAllProjectAllocationByProjectId(Long id) {
    return projectAllocationRepository.findByProjectId(id);
  }

  @Override
  public boolean existsByProjectId(Long id) {
    return projectAllocationRepository.existsByProjectId(id);
  }

  @Override
  public boolean existByProjectAllocation(Long id) {
    return projectAllocationRepository.existsById(id);
  }

  public boolean isUpdatedProjectAllocationEmployeeAndProject(Long EmployeeId, Long ProjectId,
      Long ProjectAllocationId) {
    return projectAllocationRepository.existsByEmployeeIdAndProjectIdAndIdNot(EmployeeId, ProjectId,
        ProjectAllocationId);
  }

  @Override
  public void deleteProjectAllocation(Long id) {
    projectAllocationRepository.deleteById(id);
  }

  @Override
  public List<ProjectAllocation> getAllProjectAllocationByEmployeeId(Long id) {
    return projectAllocationRepository.findByEmployeeId(id);
  }

  @Override
  public List<ProjectAllocation> getAllProjectAllocationByRoleId(Long id) {
    return projectAllocationRepository.findByRoleId(id);
  }

  @Transactional
  public List<ProjectAllocationResponse> multiSearchProjectAllocation(Pageable pageable,
      Pagination pagination, ProjectAllocationSearch projectAllocationSearch) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (Utils.isNotNullAndEmpty(projectAllocationSearch.getEmployee())) {
      QEmployee qEmployee = QEmployee.employee;
      booleanBuilder
          .and(qEmployee.firstName.containsIgnoreCase(projectAllocationSearch.getEmployee()));
    }
    if (Utils.isNotNullAndEmpty(projectAllocationSearch.getProject())) {
      QProject qProject = QProject.project;
      booleanBuilder
          .and(qProject.projectName.containsIgnoreCase(projectAllocationSearch.getProject()));
    }
    if (Utils.isNotNullAndEmpty(projectAllocationSearch.getRole())) {
      QRole qRole = QRole.role;
      booleanBuilder.and(qRole.name.containsIgnoreCase(projectAllocationSearch.getRole()));
    }

    List<ProjectAllocationResponse> projectAllocationResponses = new ArrayList<>();
    Page<ProjectAllocation> projectAllocations =
        projectAllocationRepository.findAll(booleanBuilder, pageable);
    pagination.setTotalRecords(projectAllocations.getTotalElements());
    pagination.setTotalPages(projectAllocations.getTotalPages());
    for (ProjectAllocation projectAllocation : projectAllocations.toList()) {
      ProjectAllocationResponse projectAllocationResponse = new ProjectAllocationResponse();
      BeanUtils.copyProperties(projectAllocation, projectAllocationResponse);
      projectAllocationResponse.setEmployee_id(projectAllocation.getEmployee().getId());
      projectAllocationResponse.setEmployeeName(projectAllocation.getEmployee().getFirstName());
      projectAllocationResponse.setProject_id(projectAllocation.getProject().getId());
      projectAllocationResponse.setProjectName(projectAllocation.getProject().getProjectName());
      projectAllocationResponse.setRole_id(projectAllocation.getRole().getId());
      projectAllocationResponse.setRoleName(projectAllocation.getRole().getName());
      projectAllocationResponses.add(projectAllocationResponse);
    }
    return projectAllocationResponses;
  }
}
