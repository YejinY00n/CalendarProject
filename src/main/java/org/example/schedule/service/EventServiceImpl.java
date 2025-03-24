package org.example.schedule.service;

import java.sql.Timestamp;
import java.util.List;
import org.example.schedule.dto.EventRequestDTO;
import org.example.schedule.dto.EventResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

  @Override
  public EventResponseDTO createEvent(EventRequestDTO requestDTO) {
    return null;
  }

  @Override
  public List<EventResponseDTO> findAllEvents() {
    return null;
  }

  @Override
  public List<EventResponseDTO> findAllEventsByOwnerOrEditedTime(String owner,
      Timestamp editedTime) {
    return null;
  }

  @Override
  public EventResponseDTO findEventById(Long id) {
    return null;
  }

  @Override
  public EventResponseDTO updateEvent(EventRequestDTO requestDTO) {
    return null;
  }

  @Override
  public void deleteEvent(Long id, String password) {

  }
}
