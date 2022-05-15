package com.topcoder.onlinereview.entity.ids;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "query_input_xref")
@IdClass(QueryInputId.class)
public class QueryInputXref {
  @Id
  @Column(name = "query_id")
  private Long queryId;

  @Id
  @Column(name = "input_id")
  private Long inputId;

  @Column(name = "sort_order")
  private Long sortOrder;

  @Type(type = "yes_no")
  @Column(name = "optional")
  private Boolean optional;

  @Column(name = "default_value")
  private String defaultValue;
}
