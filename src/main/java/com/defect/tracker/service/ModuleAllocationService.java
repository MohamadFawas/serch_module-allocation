package com.defect.tracker.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.entities.ModuleAllocation;
import com.defect.tracker.response.dto.ModuleAllocationResponse;
import com.defect.tracker.resquest.dto.ModuleAllocationRequest;
import com.defect.tracker.search.response.ModuleAllocationSearch;

public interface ModuleAllocationService {
	public void saveModuleAllocation(ModuleAllocationRequest moduleAllocationRequest);

	public boolean existByModuleAllocation(Long id);

	public boolean existBySubModuleId(Long subModuleId);

	public boolean existByModuleId(Long moduleId);

	boolean existsByProjectAllocatonId(Long projectAllocationId);

	public List<ModuleAllocationResponse> getAllModuleAllocation();

	public List<ModuleAllocation> getAllModuleAllocationByModuleId(Long id);

	public List<ModuleAllocation> getAllModuleAllocationBySubModuleId(Long id);

	public List<ModuleAllocation> getAllModuleAllocationByEmployeeId(Long id);

	public ModuleAllocationResponse getModuleAllocationById(Long id);

	public void deleteModuleAllocation(Long id);

	boolean existsByModuleIdAndSubModuleId(Long moduleId, Long subModuleId);

	public boolean existsBySubModule(Long subModuleId);

	public boolean existsByProjectAllocation(Long projectAllocationId);

	// public List<ModuleAllocationResponse> multiSearchModuleAllocation(Pageable
	// pageable, Pagination pagination,
	// ModuleAllocationSearch moduleAllocationSearch);
	public List<ModuleAllocationResponse> multiSearchModuleAllocation(Pageable pageable, Pagination pagination,
			ModuleAllocationSearch moduleAllocationSearch);
}
