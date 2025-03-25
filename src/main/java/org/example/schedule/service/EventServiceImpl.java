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
  /*
  1. Owner 만 2.기간만(start~end, ~end, start~) 3.전부 4.전부 없음

  1. Owner 만 --> Owner 일치 조회
  2. 전부 (Owner 있고, 기간 하나라도 있는 경우) --> Owner 소유, 기간
  2-1. 시작만 있는 경우 --> 시작~현재
  2-2. 끝만 있는 경우 --> 끝 이하 모두 조회?
  3. 전부 없음 --> 그냥 모두 조회
  ^ 도전과제랑 같이 구현
  dateRange 객체를 만들까?
   */
  @Override
  public List<EventResponseDTO> findAllEvents(
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
    int updatedRow = eventRepository.updateEvent(id, requestDTO.getTask(), requestDTO.getOwner());

    if (updatedRow == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist: id" + id);
    }

    return new EventResponseDTO(new Event(requestDTO));
  }

  @Override
  public void deleteEvent(Long id, String password) {
    int deletedRow = eventRepository.deleteEvent(id, password);

    if (deletedRow == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist: id" + id);
    }
  }
}
