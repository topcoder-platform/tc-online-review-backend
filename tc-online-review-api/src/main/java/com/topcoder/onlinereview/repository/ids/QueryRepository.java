package com.topcoder.onlinereview.repository.ids;

import com.topcoder.onlinereview.entity.ids.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository<Query, Long> {}
