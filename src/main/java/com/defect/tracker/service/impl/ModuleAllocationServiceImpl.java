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
import com.defect.tracker.entities.Module;
import com.defect.tracker.entities.ModuleAllocation;
import com.defect.tracker.entities.ProjectAllocation;
import com.defect.tracker.entities.QEmployee;
import com.defect.tracker.entities.QModule;
import com.defect.tracker.entities.QSubModule;
import com.defect.tracker.entities.SubModule;
import com.defect.tracker.repositories.ModuleAllocationRepository;
import com.defect.tracker.response.dto.ModuleAllocationResponse;
import com.defect.tracker.resquest.dto.ModuleAllocationRequest;
import com.defect.tracker.search.response.ModuleAllocationSearch;
import com.defect.tracker.service.ModuleAllocationService;
import com.defect.tracker.utils.Utils;
import com.querydsl.core.BooleanBuilder;

@Service
public class ModuleAllocationServiceImpl implements ModuleAllocationService {
  @Autowired
  private ModuleAllocationRepository moduleAllocationRepository;
  
 

  @Override
  public void saveModuleAllocation(ModuleAllocationRequest moduleAllocationRequest) {
    ModuleAllocation moduleAllocation = new ModuleAllocation();
    BeanUtils.copyProperties(moduleAllocationRequest, moduleAllocation);
    Module module = new Module();
    module.setId(moduleAllocationRequest.getModuleId());
    moduleAllocation.setModule(module);
    SubModule subModule = new SubModule();
    subModule.setId(moduleAllocationRequest.getSubModuleId());
    moduleAllocation.setSubModule(subModule);
    ProjectAllocation projectAllocation = new ProjectAllocation();
    projectAllocation.setId(moduleAllocationRequest.getProjectAllocationId());
    moduleAllocation.setEmployee(projectAllocation);
    moduleAllocationRepository.save(moduleAllocation);
  }

  @Override
  public boolean existByModuleAllocation(Long id) {
    return moduleAllocationRepository.existsById(id);
  }

  @Override
  public boolean existBySubModuleId(Long id) {
    return moduleAllocationRepository.existsBySubModuleId(id);
  }

  @Override
  public boolean existByModuleId(Long id) {
    return moduleAllocationRepository.existsByModuleId(id);
  }

  @Transactional
  public List<ModuleAllocationResponse> getAllModuleAllocation() {
    List<ModuleAllocationResponse> moduleAllocationResponses = new ArrayList<>();
    List<ModuleAllocation> moduleAllocations = moduleAllocationRepository.findAll();
    for (ModuleAllocation moduleAllocation : moduleAllocations) {
      ModuleAllocationResponse moduleAllocationResponse = new ModuleAllocationResponse();
      BeanUtils.copyProperties(moduleAllocation, moduleAllocationResponse);
      moduleAllocationResponse.setModuleId(moduleAllocation.getModule().getId());
      moduleAllocationResponse.setSubModuleId(moduleAllocation.getSubModule().getId());
      moduleAllocationResponse.setProjectAllocationId(moduleAllocation.getEmployee().getId());
      moduleAllocationResponse
          .setEmployeeName(moduleAllocation.getEmployee().getEmployee().getFirstName());
      moduleAllocationResponses.add(moduleAllocationResponse);
    }
    return moduleAllocationResponses;
  }

  @Override
  public List<ModuleAllocation> getAllModuleAllocationByModuleId(Long id) {
    return moduleAllocationRepository.findByModuleId(id);
  }

  @Override
  public List<ModuleAllocation> getAllModuleAllocationBySubModuleId(Long id) {
    return moduleAllocationRepository.findBySubModuleId(id);
  }

  @Override
  public List<ModuleAllocation> getAllModuleAllocationByEmployeeId(Long id) {
    return moduleAllocationRepository.findByEmployeeId(id);
  }

  @Override
  public ModuleAllocationResponse getModuleAllocationById(Long id) {
    ModuleAllocation moduleAllocation = moduleAllocationRepository.findById(id).get();
    ModuleAllocationResponse moduleAllocationResponse = new ModuleAllocationResponse();
    BeanUtils.copyProperties(moduleAllocation, moduleAllocationResponse);
    moduleAllocationResponse.setModuleId(moduleAllocation.getModule().getId());
    moduleAllocationResponse.setSubModuleId(moduleAllocation.getSubModule().getId());
    moduleAllocationResponse.setProjectAllocationId(moduleAllocation.getEmployee().getId());
    moduleAllocationResponse
        .setEmployeeName(moduleAllocation.getEmployee().getEmployee().getFirstName());
    return moduleAllocationResponse;
  }

  @Override
  public boolean existsByProjectAllocatonId(Long projectAllocationId) {
    return moduleAllocationRepository.existsByEmployeeId(projectAllocationId);
  }

  @Override
  public void deleteModuleAllocation(Long id) {
    moduleAllocationRepository.deleteById(id);
  }

  @Override
  public boolean existsByModuleIdAndSubModuleId(Long moduleId, Long subModuleId) {
    return moduleAllocationRepository.existsByModuleIdAndSubModuleId(moduleId, subModuleId);
  }

  @Override
  public boolean existsBySubModule(Long subModuleId) {
    return moduleAllocationRepository.existsBySubModuleId(subModuleId);
  }

  @Override
  public boolean existsByProjectAllocation(Long projectAllocationId) {
    return moduleAllocationRepository.existsByemployeeId(projectAllocationId);
  }

@Transactional
public List<ModuleAllocationResponse> multiSearchModuleAllocation(Pageable pageable, Pagination pagination,
		ModuleAllocationSearch moduleAllocationSearch) {
	BooleanBuilder booleanBuilder = new BooleanBuilder();
	//QModuleAllocation qModuleAllocation=QModuleAllocation.moduleAllocation;
	
	if(Utils.isNotNullAndEmpty(moduleAllocationSearch.getModuleName())) {
		QModule qModule=QModule.module;
		booleanBuilder.and(qModule.name.containsIgnoreCase(moduleAllocationSearch.getModuleName()));
	}
	if(Utils.isNotNullAndEmpty(moduleAllocationSearch.getSubModuleName())) {
		QSubModule qSubModule=QSubModule.subModule;
		booleanBuilder.and(qSubModule.name.containsIgnoreCase(moduleAllocationSearch.getSubModuleName()));
	}
	if(Utils.isNotNullAndEmpty(moduleAllocationSearch.getEmployeeName())) {
		QEmployee qEmployee=QEmployee.employee;
		booleanBuilder.and(qEmployee.firstName.containsIgnoreCase(moduleAllocationSearch.getEmployeeName()));
	}
	
	List<ModuleAllocationResponse> moduleAllocationResponses=new ArrayList<>();
	Page<ModuleAllocation> moduleAllocations=moduleAllocationRepository.findAll(booleanBuilder, pageable);
	pagination.setTotalRecords(moduleAllocations.getTotalElements());
	pagination.setTotalPages(moduleAllocations.getTotalPages());
	
	for(ModuleAllocation moduleAllocation : moduleAllocations.toList()) {
		ModuleAllocationResponse moduleAllocationResponse=new ModuleAllocationResponse();
		BeanUtils.copyProperties(moduleAllocation, moduleAllocationResponse);
		moduleAllocationResponse.setModuleName(moduleAllocation.getModule().getName());
		moduleAllocationResponse.setSubModuleName(moduleAllocation.getSubModule().getName());
		moduleAllocationResponses.add(moduleAllocationResponse);
	}
	
	return moduleAllocationResponses;
}
  
}
