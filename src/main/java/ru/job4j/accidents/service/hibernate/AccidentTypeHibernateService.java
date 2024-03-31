package ru.job4j.accidents.service.hibernate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.hibernate.AccidentHibernate;
import ru.job4j.accidents.repository.hibernate.AccidentTypeHibernate;
import ru.job4j.accidents.repository.jdbc.AccidentTypeJdbcTemplate;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AccidentTypeHibernateService {
    private final AccidentTypeHibernate accidentTypeHibernate;

    public Collection<AccidentType> findAll() {
        return accidentTypeHibernate.findAll();
    }
}
