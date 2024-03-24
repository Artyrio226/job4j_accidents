package ru.job4j.accidents.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class AccidentTypeJdbcTemplate {
    private final JdbcTemplate jdbc;

    public List<AccidentType> findAll() {
        return jdbc.query("select id, name from types",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                });
    }

    public Optional<AccidentType> findById(int id) {
        var result = jdbc.query("select id, name from types where id = ?",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                }, id);
        return Optional.of(result.get(0));
    }
}
