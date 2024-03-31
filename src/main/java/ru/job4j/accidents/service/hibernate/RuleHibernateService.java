package ru.job4j.accidents.service.hibernate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.hibernate.RuleHibernate;
import ru.job4j.accidents.repository.jdbc.RuleJdbcTemplate;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RuleHibernateService {
    private final RuleHibernate ruleHibernate;

    public Collection<Rule> findAll() {
        return ruleHibernate.findAll();
    }

    public Optional<Rule> findById(int id) {
        return ruleHibernate.findById(id);
    }
}
