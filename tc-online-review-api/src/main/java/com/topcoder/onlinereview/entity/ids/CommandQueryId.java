package com.topcoder.onlinereview.entity.ids;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommandQueryId implements Serializable {
  private Long commandId;
  private Long queryId;
}
