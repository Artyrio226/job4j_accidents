package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentMem accidentMem;
    private final RuleService ruleService;

    public Collection<Accident> findAll() {
        return accidentMem.findAll();
    }

    public Optional<Accident> create(Accident accident, String[] ids) {
        Accident newAccident = new Accident();
        newAccident.setName(accident.getName());
        newAccident.setText(accident.getText());
        newAccident.setAddress(accident.getAddress());

        AccidentType accidentType = new AccidentType();
        accidentType.setId(accident.getType().getId());
        accidentType.setName(accident.getType().getName());
        newAccident.setType(accidentType);

        Set<Rule> set = getSet(ids);
        newAccident.setRules(set);
        return accidentMem.create(newAccident);
    }

    public Optional<Accident> findById(int id) {
        return accidentMem.findById(id);
    }

    public Optional<Accident> update(Accident accident, String[] ids) {
        Set<Rule> set = getSet(ids);
        accident.setRules(set);
        return accidentMem.update(accident);
    }

    private Set<Rule> getSet(String[] ids) {
        Set<Rule> set = new HashSet<>();
        for (String str: ids) {
            set.add(ruleService.findById(Integer.parseInt(str)).get());
        }
        return set;
    }
}
