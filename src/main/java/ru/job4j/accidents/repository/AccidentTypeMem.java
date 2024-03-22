package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class AccidentTypeMem {
    private final Map<Integer, AccidentType> accidentTypeMap = new ConcurrentHashMap<>(Map.of(
            1, new AccidentType(1, "Две машины"),
            2, new AccidentType(2, "Машина и человек"),
            3, new AccidentType(3, "Машина и велосипед")
    ));

    public Collection<AccidentType> findAll() {
        return new ArrayList<>(accidentTypeMap.values());
    }

    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypeMap.get(id));
    }
}
