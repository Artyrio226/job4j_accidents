package ru.job4j.accidents.repository.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    public Optional<Accident> create(Accident accident) {
        String sqlQuery = "insert into accidents (name, text, address, types_id) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, accident.getName());
            stmt.setString(2, accident.getText());
            stmt.setString(3, accident.getAddress());
            stmt.setInt(4, accident.getType().getId());
            return stmt;
        }, keyHolder);
        accident.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return Optional.of(accident);
    }

    public List<Accident> findAll() {
        return jdbc.query("select a.id, a.name, a.text, a.address, a.types_id, t.name "
                          + "from accidents a join types t on a.types_id = t.id",
                getRowMapper());
    }

    public Optional<Accident> update(Accident accident) {
        jdbc.update("update accidents set name = ?, text = ?, address = ?, types_id = ? where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId());
        return Optional.of(accident);
    }

    public Optional<Accident> findById(int id) {
        var result = jdbc.query("select a.id, a.name, a.text, a.address, a.types_id, t.name "
                                + "from accidents a join types t on t.id = a.types_id where a.id = ?",
                getRowMapper(),
                id);
        return Optional.of(result.get(0));
    }

    private static RowMapper<Accident> getRowMapper() {
        return (rs, row) -> {
            Accident accident = new Accident();
            accident.setId(rs.getInt(1));
            accident.setName(rs.getString(2));
            accident.setText(rs.getString(3));
            accident.setAddress(rs.getString(4));

            AccidentType accidentType = new AccidentType();
            accidentType.setId(rs.getInt(5));
            accidentType.setName(rs.getString(6));
            accident.setType(accidentType);
            return accident;
        };
    }
}
