package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>(Map.of(
            1, new Accident(1, "Антон Сидоров", "Проезд на красный сигнал светофора.", "ул. Белова, д. 12"),
            2, new Accident(2, "Егор Иванов", "Пересечение сплошной линии.", "ул. Лесная, д. 5"),
            3, new Accident(3, "Иван Антонов", "Остановка в неположенном месте.", "ул. Заречная, д. 27")
    ));

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    public Optional<Accident> create(Accident accident) {
        return Optional.ofNullable(accidents.put(accident.getId(), accident));
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }
}
