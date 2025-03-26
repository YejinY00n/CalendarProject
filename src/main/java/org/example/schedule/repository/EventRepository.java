package org.example.schedule.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.example.schedule.entity.Event;

public interface EventRepository {

  // 생성
  Event createEvent(Event event);

  // 모든 일정 조회
  List<Event> findAllEvents();

  // 작성자의 모든 일정 조회
  List<Event> findAllEventsByOwner(String owner);

  // 기간 내 모든 일정 조회
  List<Event> findAllEventsBetweenDateRange(LocalDateTime startDate,
      LocalDateTime endDate);

  // 기간 내 작성자의 모든 일정 조회
  List<Event> findAllEventsByOwnerBetweenDateRange(
      String owner,
      LocalDateTime startDate,
      LocalDateTime endDate);

  // 단건 조회
  Event findEventById(Long id);

  // 일정 수정 (id, 할일, 작성자명, 비번)
  int updateEvent(Long id, String task, String owner);

  // 일정 삭제 (id, 비번)
  int deleteEvent(Long id, String password);

}
