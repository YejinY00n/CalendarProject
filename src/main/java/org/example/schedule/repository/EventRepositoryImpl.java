package org.example.schedule.repository;

import java.sql.Timestamp;
import java.util.List;
import org.example.schedule.entity.Event;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepositoryImpl implements EventRepository {

  @Override
  public Event createEvent(Event event) {
    return null;
  }

  @Override
  public List<Event> findAllEvents() {
    return null;
  }

  @Override
  public List<Event> findAllEventsByOwnerOrEditedTime(String owner, Timestamp editedTime) {
    return null;
  }

  @Override
  public Event findEventById(Long id) {
    return null;
  }

  @Override
  public Event updateEvent(Event event) {
    return null;
  }

  @Override
  public void deleteEvent(Long id, String password) {

  }
}
