package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeMem;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AccidentTypeService {
    private final AccidentTypeMem typeMem;

    public Collection<AccidentType> findAll() {
        return typeMem.findAll();
    }
}
