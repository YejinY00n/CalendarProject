package org.example.schedule.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.schedule.dto.EventRequestDTO;

@Getter
@AllArgsConstructor
public class Event {

  @Setter
  private Long id;
  private String task;
  private LocalDateTime createdTime;
  private LocalDateTime editedTime;
  private String owner;
  private String password;

  public Event(EventRequestDTO requestDTO) {
    task = requestDTO.getTask();
    owner = requestDTO.getOwner();
    password = requestDTO.getPassword();
    createdTime = LocalDateTime.now();
    editedTime = LocalDateTime.from(createdTime);       // 초기 생성 시 생성 날짜와 수정 날짜 동일하도록 설정(객체 복사)
  }
}
