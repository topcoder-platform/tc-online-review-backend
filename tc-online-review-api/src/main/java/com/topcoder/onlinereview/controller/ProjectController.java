package com.topcoder.onlinereview.controller;

import com.topcoder.onlinereview.component.exception.BaseException;
import com.topcoder.onlinereview.dto.EditProjectResponse;
import com.topcoder.onlinereview.dto.ListProjectResponse;
import com.topcoder.onlinereview.service.EditProjectService;
import com.topcoder.onlinereview.service.ListProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
  @Autowired private ListProjectService listProjectService;
  @Autowired private EditProjectService editProjectService;

  @GetMapping("projects")
  public ListProjectResponse listProjects(
      @RequestParam Integer activeTab,
      @RequestParam String scope,
      @RequestParam Long userId,
      @RequestParam String role)
      throws BaseException {
    return listProjectService.listProjects(activeTab, scope, userId, role);
  }

  @GetMapping("projects/{projectId}")
  public EditProjectResponse getProjectDetail(
      @PathVariable Long projectId, @RequestParam Long userId, @RequestParam String role)
      throws BaseException {
    return editProjectService.editProject(projectId, role, userId);
  }
}
