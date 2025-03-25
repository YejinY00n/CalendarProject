package org.example.schedule.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.example.schedule.entity.Event;

public interface EventRepository {

  // 생성
  Event createEvent(Event event);


  // 조건 일치 일정들 조회 (수정 날짜, 작성자명)
  List<Event> findAllEventsByOwnerOrEditedTime(
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
