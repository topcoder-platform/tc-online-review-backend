package com.topcoder.onlinereview.entity.ids;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "query")
public class Query {
  @Id
  @Column(name = "query_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "text")
  private String text;

  @Column(name = "name")
  private String name;

  @Column(name = "ranking")
  private Integer ranking;

  @Column(name = "column_index")
  private Integer columnIndex;
}
