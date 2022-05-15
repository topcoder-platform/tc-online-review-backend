package com.topcoder.onlinereview.repository.ids;

import com.topcoder.onlinereview.entity.ids.QueryInputId;
import com.topcoder.onlinereview.entity.ids.QueryInputXref;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueryInputXrefRepository extends JpaRepository<QueryInputXref, QueryInputId> {
  List<QueryInputXref> findAllByQueryIdIn(Iterable<Long> queryIds);
}
