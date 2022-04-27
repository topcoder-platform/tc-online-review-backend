package com.topcoder.onlinereview.repository.ids;

import com.topcoder.onlinereview.entity.ids.Command;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandRepository extends JpaRepository<Command, Long> {
  List<Command> findAllByCommandDesc(String commandDesc);
}
