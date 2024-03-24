package ru.job4j.accidents.repository.mem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@RequiredArgsConstructor
public class AccidentMem {
    private static final AtomicInteger COUNT = new AtomicInteger(4);
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>(Map.of(
            1, new Accident(1,
                    "Антон Сидоров",
                    "Проезд на красный сигнал светофора.",
                    "ул. Белова, д. 12",
                    new AccidentType(1, "Две машины"),
                    new HashSet<>(Set.of(new Rule(1, "Статья. 1")))),
            2, new Accident(2,
                    "Егор Иванов",
                    "Пересечение сплошной линии.",
                    "ул. Лесная, д. 5",
                    new AccidentType(1, "Две машины"),
                    new HashSet<>(Set.of(new Rule(2, "Статья. 2")))),
            3, new Accident(3,
                    "Иван Антонов",
                    "Остановка в неположенном месте.",
                    "ул. Заречная, д. 27",
                    new AccidentType(2, "Машина и человек"),
                    new HashSet<>(Set.of(new Rule(3, "Статья. 3"))))
    ));

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    public Optional<Accident> create(Accident accident) {
        int id = accident.getId();
        accident.setId(id == 0 ? COUNT.getAndIncrement() : id);
        return Optional.ofNullable(accidents.put(accident.getId(), accident));
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    public Optional<Accident> update(Accident accident) {
        return Optional.ofNullable(accidents.computeIfPresent(accident.getId(), (a, b) -> accident));
    }
}
