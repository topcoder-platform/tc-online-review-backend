package com.topcoder.onlinereview.controller;

import com.topcoder.onlinereview.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class TestController {
  @Autowired private QueryService queryService;

  @GetMapping("test")
  public ResponseEntity<?> test() {
      var map = new HashMap<String, String>();
      map.put("stid", "1");
    return ResponseEntity.ok(queryService.executeCommand("tcs_projects_by_status", map));
  }
}
