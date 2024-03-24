package ru.job4j.accidents.repository.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class RuleJdbcTemplate {
    private final JdbcTemplate jdbc;

    public Set<Rule> create(int id, Set<Rule> rules) {
        for (Rule rule: rules) {
            jdbc.update("insert into accidents_rules (accidents_id, rules_id) values (?, ?)",
                    id,
                    rule.getId());
        }
        return rules;
    }

    public List<Rule> findAll() {
        return jdbc.query("select id, name from rules",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    public Optional<Rule> findById(int id) {
        var result = jdbc.query("select id, name from rules where id = ?",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                }, id);
        return Optional.of(result.get(0));
    }

    public Set<Rule> update(int id, Set<Rule> rules) {
        jdbc.update("delete from accidents_rules where accidents_id = ?", id);
        for (Rule rule: rules) {
            jdbc.update("insert into accidents_rules (accidents_id, rules_id) values (?, ?)",
                    id,
                    rule.getId());
        }
        return rules;
    }
}
