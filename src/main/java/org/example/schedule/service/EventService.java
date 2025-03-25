package org.example.schedule.service;

import java.time.LocalDateTime;
import java.util.List;
import org.example.schedule.dto.EventRequestDTO;
import org.example.schedule.dto.EventResponseDTO;

public interface EventService {

  // 생성
  EventResponseDTO createEvent(EventRequestDTO requestDTO);


  // 조건 일치 일정들 조회 (수정 날짜, 작성자명)
  List<EventResponseDTO> findAllEventsByOwnerOrEditedTime(
      String owner,
      LocalDateTime startDate,
      LocalDateTime endDate);

  // 단건 조회
  EventResponseDTO findEventById(Long id);

  // 일정 수정 (id, 할일, 작성자명, 비번)
  EventResponseDTO updateEvent(Long id, EventRequestDTO requestDTO);

  // 일정 삭제 (id, 비번)
  void deleteEvent(Long id, String password);
}
