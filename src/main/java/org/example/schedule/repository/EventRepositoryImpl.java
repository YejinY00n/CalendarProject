package org.example.schedule.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.example.schedule.entity.Event;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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

  @Override
  public List<Event> findAllEventsByOwnerOrEditedTime(
      String owner,
      LocalDateTime startDate,
      LocalDateTime endDate) {
    return jdbcTemplate.query(
        "SELECT * FROM event WHERE owner = ? AND edited_time BETWEEN ? AND ?",
        eventRowMapper(), owner, startDate, endDate);
  }

  // TODO: Null 경우 -> Optional 처리
  @Override
  public Event findEventById(Long id) {
    return jdbcTemplate.query("SELECT * FROM event WHERE id = ?", eventRowMapper(), id).get(0);
  }

  @Override
  public int updateEvent(Long id, String task, String owner) {
    return jdbcTemplate.update("UPDATE event SET task = ?, owner = ? WHERE id = ?", task, owner,
        id);
  }

  @Override
  public void deleteEvent(Long id, String password) {

  }
}
