package ru.job4j.accidents.repository.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.*;

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
        setRules(accident.getId(), accident.getRules());
        return Optional.of(accident);
    }

    private void setRules(int id, Set<Rule> rules) {
        List<Object[]> args = rules.stream().map(rule -> new Object[]{id, rule.getId()}).toList();
        jdbc.batchUpdate("insert into accidents_rules (accidents_id, rules_id) values (?, ?)", args);
    }

    public List<Accident> findAll() {
        Map<Integer, Accident> map = new HashMap<>();
        return jdbc.query("select a.id, a.name, a.text, a.address, a.types_id, t.name, r.id, r.name "
                          + "from accidents a join types t on a.types_id = t.id "
                          + "join accidents_rules ar on a.id = accidents_id "
                          + "join rules r on ar.rules_id = r.id",
                getRowMapper(map));
    }

    public Optional<Accident> update(Accident accident) {
        int id = accident.getId();
        jdbc.update("update accidents set name = ?, text = ?, address = ?, types_id = ? where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                id);
        jdbc.update("delete from accidents_rules where accidents_id = ?", id);
        setRules(id, accident.getRules());
        return Optional.of(accident);
    }

    public Optional<Accident> findById(int id) {
        Map<Integer, Accident> map = new HashMap<>();
        return jdbc.query("select a.id, a.name, a.text, a.address, a.types_id, t.name, r.id, r.name "
                                + "from accidents a join types t on a.types_id = t.id "
                                + "join accidents_rules ar on a.id = accidents_id "
                                + "join rules r on ar.rules_id = r.id where a.id = ?",
                getRowMapper(map),
                id).stream().findFirst();
    }

    private static RowMapper<Accident> getRowMapper(Map<Integer, Accident> map) {
        return (rs, row) -> {
            int id = rs.getInt(1);
            Accident accident = map.get(id);
            if (accident == null) {
                accident = new Accident();
                accident.setId(id);
                accident.setName(rs.getString(2));
                accident.setText(rs.getString(3));
                accident.setAddress(rs.getString(4));

                AccidentType accidentType = new AccidentType(rs.getInt(5), rs.getString(6));
                accident.setType(accidentType);
                accident.setRules(new HashSet<>());
                map.put(id, accident);
            }
            Rule rule = new Rule();
            rule.setId(rs.getInt(7));
            rule.setName(rs.getString(8));
            accident.getRules().add(rule);
            return accident;
        };
    }
}
