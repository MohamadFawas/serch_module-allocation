package com.defect.tracker.resquest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectAllocationRequest {
  private Long id;
  private Long employee_id;
  private String contribution;
  private Long project_id;
  private Long role_id;
}
