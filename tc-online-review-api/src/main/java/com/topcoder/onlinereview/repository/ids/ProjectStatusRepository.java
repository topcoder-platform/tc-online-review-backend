package com.topcoder.onlinereview.repository.ids;

import com.topcoder.onlinereview.entity.ids.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {}
