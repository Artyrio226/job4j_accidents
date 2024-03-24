package ru.job4j.accidents.repository.mem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class RuleMem {
    private final Map<Integer, Rule> ruleMap = new ConcurrentHashMap<>(Map.of(
            1, new Rule(1, "Статья. 1"),
            2, new Rule(2, "Статья. 2"),
            3, new Rule(3, "Статья. 3")
    ));

    public Collection<Rule> findAll() {
        return new ArrayList<>(ruleMap.values());
    }

    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(ruleMap.get(id));
    }
}
