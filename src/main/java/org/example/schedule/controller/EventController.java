package org.example.schedule.controller;

import java.time.LocalDate;
import java.util.List;
import org.example.schedule.dto.EventRequestDTO;
import org.example.schedule.dto.EventResponseDTO;
import org.example.schedule.dto.PasswordRequestDTO;
import org.example.schedule.service.EventService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController {

  private final EventService eventService;

  // 생성자
  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  // 생성
  @PostMapping
  public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventRequestDTO requestDTO) {
    return new ResponseEntity<>(eventService.createEvent(requestDTO), HttpStatus.CREATED);
  }

  // 조건 일치 일정들 조회 (수정 날짜, 작성자명)
  @GetMapping()
  public List<EventResponseDTO> findAllEvents(
      @RequestParam(required = false) String owner,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
    return eventService.findAllEvents(owner, startDate != null ? startDate.atTime(0, 0, 0) : null,
        endDate != null ? endDate.atTime(23, 59, 59) : null);
  }

  // 단건 조회
  @GetMapping("/{id}")
  public EventResponseDTO findEventById(@PathVariable Long id) {
    return eventService.findEventById(id);
  }

  // TODO: 비밀번호 받는 부분 암호화 기능 추가
  // 일정 수정 (id, 할일, 작성자명, 비번)
  @PutMapping("/{id}")
  public ResponseEntity<EventResponseDTO> updateEvent(
      @PathVariable Long id,
      @RequestBody EventRequestDTO requestDTO) {
    return new ResponseEntity<>(eventService.updateEvent(id, requestDTO), HttpStatus.OK);
  }

  // 일정 삭제 (id, 비번)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEvent(@PathVariable Long id,
      @RequestBody PasswordRequestDTO password) {
    eventService.deleteEvent(id, password);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
