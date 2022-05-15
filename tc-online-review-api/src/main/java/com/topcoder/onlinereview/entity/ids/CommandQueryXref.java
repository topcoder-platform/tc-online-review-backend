package com.topcoder.onlinereview.entity.ids;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "command_query_xref")
@IdClass(CommandQueryId.class)
public class CommandQueryXref {
  @Id
  @Column(name = "command_id")
  private Long commandId;

  @Id
  @Column(name = "query_id")
  private Long queryId;

  @Column(name = "sort_order")
  private Long sortOrder;
}
