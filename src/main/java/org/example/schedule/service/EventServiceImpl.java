package org.example.schedule.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.example.schedule.dto.EventRequestDTO;
import org.example.schedule.dto.EventResponseDTO;
import org.example.schedule.entity.Event;
import org.example.schedule.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;

  // 생성자
  public EventServiceImpl(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Override
  public EventResponseDTO createEvent(EventRequestDTO requestDTO) {
    return new EventResponseDTO(eventRepository.createEvent(new Event(requestDTO)));
  }

  // TODO: List Entity -> List DTO 메소드화 필요
  // TODO: 추후 제약 조건 추가 (start<=endDate, endDate 는 현재까지여야 함)
  // TODO: 입력값 검증 추가 필요, 입력값에 따른 repository 호출 변경 필요
  @Override
  public List<EventResponseDTO> findAllEventsByOwnerOrEditedTime(
      String owner,
      LocalDateTime startDate,
      LocalDateTime endDate) {
    List<EventResponseDTO> collect = eventRepository.findAllEventsByOwnerOrEditedTime(owner,
            startDate, endDate).stream()
        .map(EventResponseDTO::new)
        .collect(Collectors.toList());
    System.out.println(collect.get(0).getCreatedTime().getClass());
    return collect;
  }

  @Override
  public EventResponseDTO findEventById(Long id) {
    return new EventResponseDTO(eventRepository.findEventById(id));
  }

  // TODO: 값 검증 로직 추가
  // TODO: 비밀번호 검증 로직 추가
  // TODO: Update 리퀘스트 DTO 새로 생성 필요 (생성, 수정날짜, id..) 혹은 DTO 생성자
  @Override
  public EventResponseDTO updateEvent(Long id, EventRequestDTO requestDTO) {
    int updateRow = eventRepository.updateEvent(id, requestDTO.getTask(), requestDTO.getOwner());

    if (updateRow == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist: id" + id);
    }

    return new EventResponseDTO(new Event(requestDTO));
  }

  @Override
  public void deleteEvent(Long id, String password) {

  }
}
