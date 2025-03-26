package org.example.schedule.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.example.schedule.dto.EventRequestDTO;
import org.example.schedule.dto.EventResponseDTO;
import org.example.schedule.dto.PasswordRequestDTO;
import org.example.schedule.entity.Event;
import org.example.schedule.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;

  // 생성자
  public EventServiceImpl(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  // 일정을 생성하는 메소드
  @Override
  public EventResponseDTO createEvent(EventRequestDTO requestDTO) {
    // 일정과 작성자 공백 or null 검증
    if ((requestDTO.getOwner() == null || requestDTO.getOwner().isEmpty()) && (
        requestDTO.getTask() == null || requestDTO.getTask().isEmpty())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "일정 혹은 작성자를 입력해주세요.");
    }

    // 비밀번호 공백 검증
    if ((requestDTO.getPassword() == null || requestDTO.getPassword().isEmpty())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 필수 입력값입니다.");
    }

    return new EventResponseDTO(eventRepository.createEvent(new Event(requestDTO)));
  }

  // 조건(작성자, 기간)에 맞는 모든 일정을 조회하는 메소드
  @Override
  public List<EventResponseDTO> findAllEvents(
      String owner,
      LocalDateTime startDate,
      LocalDateTime endDate) {

    List<Event> results;

    if (isValidDateRange(startDate, endDate)) {
      // 작성자의 기간 내의 일정 조회
      if (owner != null) {
        results = eventRepository.findAllEventsByOwnerBetweenDateRange(owner, startDate, endDate);
      }
      // 기간 내 모든 일정 조회
      else {
        results = eventRepository.findAllEventsBetweenDateRange(startDate, endDate);
      }
    } else {
      // 작성자의 모든 일정 조회
      if (owner != null) {
        results = eventRepository.findAllEventsByOwner(owner);
      }
      // 모든 일정 조회 (조건x)
      else {
        results = eventRepository.findAllEvents();
      }
    }

    // List<Event> --> List<EventResponseDTO>
    return results.stream()
        .map(EventResponseDTO::new)
        .collect(Collectors.toList());
  }

  // Id를 통해 단건 일정을 조회하는 메소드
  @Override
  public EventResponseDTO findEventById(Long id) {
    return new EventResponseDTO(eventRepository.findEventById(id));
  }

  // 일정을 업데이트하는 메소드
  @Override
  @Transactional
  public EventResponseDTO updateEvent(Long id, EventRequestDTO requestDTO) {
    // 일정과 작성자 공백 or null 검증
    if ((requestDTO.getOwner() == null || requestDTO.getOwner().isEmpty()) && (
        requestDTO.getTask() == null || requestDTO.getTask().isEmpty())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "일정 혹은 작성자를 입력해주세요.");
    }

    // 비밀번호 검증
    if (!isValidPassword(id, requestDTO.getPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다.");
    }

    // 일정 수정
    int updatedRow = eventRepository.updateEvent(id, requestDTO.getTask(), requestDTO.getOwner());

    // 수정된 일정이 없을 경우
    if (updatedRow == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "일정이 존재하지 않습니다. (ID: " + id + " )");
    }

    // 데이터가 정상적으로 수정되었는지 조회 후 반환
    return new EventResponseDTO(eventRepository.findEventById(id));
  }

  // 일정을 삭제하는 메소드
  @Override
  public void deleteEvent(Long id, PasswordRequestDTO passwordRequestDTO) {
    String password = passwordRequestDTO.getPassword();

    // 비밀번호 검증
    if (!isValidPassword(id, password)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다.");
    }

    // 일정 삭제
    int deletedRow = eventRepository.deleteEvent(id, password);

    // 삭제 대상 일정이 없을 경우
    if (deletedRow == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다. (ID: " + id + " )");
    }
  }

  // 유효한 기간인지 검증하는 메소드
  private boolean isValidDateRange(LocalDateTime startTime, LocalDateTime endTime) {
    // 조회 시작일, 끝일 중 하나라도 없다면
    if (startTime == null || endTime == null) {
      return false;
    }
    // 조회 시작일이 끝일보다 나중이라면
    else {
      return !startTime.isAfter(endTime);
    }
  }

  // 선택한 일정의 비밀번호와 일치 여부 검증
  private boolean isValidPassword(Long id, String password) {
    return eventRepository.findEventById(id).getPassword().equals(password);
  }
}
