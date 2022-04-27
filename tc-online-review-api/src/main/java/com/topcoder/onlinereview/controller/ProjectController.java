package com.topcoder.onlinereview.controller;

import com.topcoder.onlinereview.dto.ProjectDto;
import com.topcoder.onlinereview.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {
  @Autowired private ProjectService projectService;

  @GetMapping("projects")
  public List<ProjectDto> listProjects(
      @RequestParam(required = false, defaultValue = "1") String stid) {
    return projectService.getProjects(stid);
  }
}
