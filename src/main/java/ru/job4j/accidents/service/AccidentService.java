package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class AccidentService {
    private static final AtomicInteger COUNT = new AtomicInteger(4);
    private final AccidentMem accidentMem;

    public Collection<Accident> findAll() {
        return accidentMem.findAll();
    }

    public Optional<Accident> create(Accident accident) {
        Accident newAccident = new Accident();
        int id = accident.getId();
        newAccident.setId(id == 0 ? COUNT.getAndIncrement() : id);
        newAccident.setName(accident.getName());
        newAccident.setText(accident.getText());
        newAccident.setAddress(accident.getAddress());
        AccidentType accidentType = new AccidentType();
        accidentType.setId(accident.getType().getId());
        accidentType.setName(accident.getType().getName());
        newAccident.setType(accidentType);
        return accidentMem.create(newAccident);
    }

    public Optional<Accident> findById(int id) {
        return accidentMem.findById(id);
    }
}
