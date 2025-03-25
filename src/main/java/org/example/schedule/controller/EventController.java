package org.example.schedule.controller;

import java.time.LocalDate;
import java.util.List;
import org.example.schedule.dto.EventRequestDTO;
import org.example.schedule.dto.EventResponseDTO;
import org.example.schedule.service.EventService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  // TODO: LocalDate -> LocalDateTime 로직 이동시키기 고려
  @GetMapping()
  List<EventResponseDTO> findAllEventsByOwnerOrEditedTime(
      @RequestParam String owner,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
    return eventService.findAllEventsByOwnerOrEditedTime(owner, startDate.atTime(0, 0, 0),
        endDate.atTime(23, 59, 59));
  }

  // 단건 조회
  @GetMapping("/{id}")
  EventResponseDTO findEventById(@PathVariable Long id) {
    return eventService.findEventById(id);
  }

  // 일정 수정 (id, 할일, 작성자명, 비번)
  @PutMapping("/{id}")
  ResponseEntity<EventResponseDTO> updateEvent(
      @PathVariable Long id,
      @RequestBody EventRequestDTO requestDTO) {
    return new ResponseEntity<>(eventService.updateEvent(id, requestDTO), HttpStatus.OK);
  }

//  // 일정 삭제 (id, 비번)
//  void deleteEvent(Long id, String password);
}
