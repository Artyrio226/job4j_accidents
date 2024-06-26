package ru.job4j.accidents.service.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.jdbc.AccidentTypeJdbcTemplate;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AccidentTypeService {
    private final AccidentTypeJdbcTemplate accidentTypeRepostiory;

    public Collection<AccidentType> findAll() {
        return accidentTypeRepostiory.findAll();
    }
}
