package com.topcoder.onlinereview.controller;

import com.topcoder.onlinereview.component.exception.BaseException;
import com.topcoder.onlinereview.dto.ListProjectResponse;
import com.topcoder.onlinereview.service.ListProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
  @Autowired private ListProjectService listProjectService;

  @GetMapping("projects")
  public ListProjectResponse listProjects(
      @RequestParam(required = false, defaultValue = "1") String stid) throws BaseException {
    return listProjectService.listProjects(1, "my", 132456L, "Global Manager");
  }
}
