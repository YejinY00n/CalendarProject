package org.example.schedule.repository;

import java.sql.Timestamp;
import java.util.List;
import org.example.schedule.dto.EventRequestDTO;
import org.example.schedule.dto.EventResponseDTO;
import org.example.schedule.entity.Event;

public interface EventRepository {

  // 생성
  Event createEvent(Event event);

  // 전체 조회
  List<Event> findAllEvents();

  // 조건 일치 일정들 조회 (수정 날짜, 작성자명)
  List<Event> findAllEventsByOwnerOrEditedTime(String owner, Timestamp editedTime);

  // 단건 조회
  Event findEventById(Long id);

  // 일정 수정 (id, 할일, 작성자명, 비번)
  Event updateEvent(Event event);

  // 일정 삭제 (id, 비번)
  void deleteEvent(Long id, String password);

}
