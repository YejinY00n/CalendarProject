package org.example.schedule.entity;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Event {

  private Long id;
  private String task;
  private Timestamp createdTime;
  private Timestamp editedTime;
  private String owner;
  private String password;
}
