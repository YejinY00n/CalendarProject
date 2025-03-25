package org.example.schedule.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import org.example.schedule.entity.Event;

@Getter
public class EventResponseDTO {

  private Long id;
  private String task;
  private LocalDateTime createdTime;
  private LocalDateTime editedTime;
  private String owner;

  public EventResponseDTO(Event event) {
    this.id = event.getId();
    this.task = event.getTask();
    this.createdTime = event.getCreatedTime();
    this.editedTime = event.getEditedTime();
    this.owner = event.getOwner();
  }
}
