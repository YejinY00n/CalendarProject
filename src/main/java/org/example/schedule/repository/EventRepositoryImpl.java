package org.example.schedule.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.example.schedule.entity.Event;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class EventRepositoryImpl implements EventRepository {

  private final JdbcTemplate jdbcTemplate;

  public EventRepositoryImpl(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public Event createEvent(Event event) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    jdbcInsert.withTableName("event").usingGeneratedKeyColumns("id");

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("task", event.getTask());
    parameters.put("owner", event.getOwner());
    parameters.put("password", event.getPassword());
    parameters.put("created_time", event.getCreatedTime());
    parameters.put("edited_time", event.getEditedTime());

    // 저장 후 생성된 key 값을 Number 타입으로 반환
    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
    event.setId(key.longValue());

    return event;
  }

  private RowMapper<Event> eventRowMapper() {
    return new RowMapper<Event>() {
      @Override
      public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        // 스키마 컬럼 순서대로 값 가져와야 한다.
        return new Event(
            rs.getLong("id"),
            rs.getString("task"),
            (rs.getTimestamp("created_time")).toLocalDateTime(),
            rs.getTimestamp("edited_time").toLocalDateTime(),
            rs.getString("owner"),
            rs.getString("password")
        );
      }
    };
  }

  // 모든 일정 조회
  @Override
  public List<Event> findAllEvents() {
    return Optional.ofNullable(jdbcTemplate.query(
            "SELECT * FROM event", eventRowMapper()))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "일정이 존재하지 않습니다. "));
  }

  // 작성자의 모든 일정 조회
  @Override
  public List<Event> findAllEventsByOwner(String owner) {
    return Optional.ofNullable(jdbcTemplate.query(
        "SELECT * FROM event WHERE owner = ?", eventRowMapper(), owner)
    ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
        owner + " 님의 일정이 없습니다."));
  }

  // 기간 내 모든 일정 조회
  @Override
  public List<Event> findAllEventsBetweenDateRange(
      LocalDateTime startDate, LocalDateTime endDate) {
    return Optional.ofNullable(jdbcTemplate.query(
            "SELECT * FROM event WHERE edited_time BETWEEN ? AND ?",
            eventRowMapper(), startDate, endDate))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "조회한 기간 내 일정이 존재하지 않습니다. "));
  }

  // 기간 내 작성자의 모든 일정 조회
  @Override
  public List<Event> findAllEventsByOwnerBetweenDateRange(
      String owner,
      LocalDateTime startDate,
      LocalDateTime endDate) {
    return Optional.ofNullable(jdbcTemplate.query(
            "SELECT * FROM event WHERE owner = ? AND edited_time BETWEEN ? AND ?",
            eventRowMapper(), owner, startDate, endDate))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "조회한 기간 내 " + owner + " 님의 일정이 존재하지 않습니다. "));
  }

  // ID로 선택한 일정을 조회하는 메소드
  @Override
  public Event findEventById(Long id) {
    return jdbcTemplate.query("SELECT * FROM event WHERE id = ?", eventRowMapper(), id).stream()
        .findAny()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "일정이 존재하지 않습니다. (ID: " + id + " )"));
  }

  // 일정을 업데이트하는 메소드
  @Override
  public int updateEvent(Long id, String task, String owner) {
    return jdbcTemplate.update("UPDATE event SET task = ?, owner = ?, edited_time = ? WHERE id = ?",
        task, owner, LocalDateTime.now(), id);
  }

  // 일정을 삭제하는 메소드
  @Override
  public int deleteEvent(Long id, String password) {
    return jdbcTemplate.update("DELETE FROM event WHERE id = ?", id);
  }
}
