package com.topcoder.onlinereview.repository.ids;

import com.topcoder.onlinereview.entity.ids.CommandQueryId;
import com.topcoder.onlinereview.entity.ids.CommandQueryXref;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandQueryXrefRepository
    extends JpaRepository<CommandQueryXref, CommandQueryId> {
  List<CommandQueryXref> findAllByCommandIdIn(Iterable<Long> commandIds);
}
