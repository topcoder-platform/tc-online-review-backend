package com.topcoder.onlinereview.repository.oltp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectRepositoryTest {

  @Autowired private ProjectRepository projectRepository;

  @Test
  void getProjects() {
    var projects = projectRepository.findAll();
    System.out.println(projects.size());
  }
}
