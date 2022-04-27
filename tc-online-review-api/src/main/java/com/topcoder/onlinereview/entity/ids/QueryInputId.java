package com.topcoder.onlinereview.entity.ids;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryInputId implements Serializable {
  private Long queryId;
  private Long inputId;
}
