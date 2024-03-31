package ru.job4j.accidents.service.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.jdbc.RuleJdbcTemplate;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleJdbcTemplate ruleRepository;

    public Collection<Rule> findAll() {
        return ruleRepository.findAll();
    }

    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }
}
