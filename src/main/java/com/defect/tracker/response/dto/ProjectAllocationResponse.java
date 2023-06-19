package com.defect.tracker.response.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectAllocationResponse {
  private Long id;
  private Long employee_id;
  private String employeeName;
  private String contribution;
  private Long project_id;
  private String projectName;
  private Long role_id;
  private String roleName;
}
