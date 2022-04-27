package com.topcoder.onlinereview.repository.oltp;

import com.topcoder.onlinereview.entity.oltp.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {}
