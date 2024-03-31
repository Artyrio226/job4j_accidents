package ru.job4j.accidents.service.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.jdbc.AccidentJdbcTemplate;
import ru.job4j.accidents.repository.jdbc.AccidentTypeJdbcTemplate;
import ru.job4j.accidents.repository.jdbc.RuleJdbcTemplate;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentJdbcTemplate accidentsRepostiory;
    private final RuleJdbcTemplate ruleRepostiory;
    private final AccidentTypeJdbcTemplate accidentTypeRepostiory;

    public Collection<Accident> findAll() {
        return accidentsRepostiory.findAll();
    }

    public Optional<Accident> create(Accident accident, String[] ids) {
        var newAccident = new Accident();
        newAccident.setName(accident.getName());
        newAccident.setText(accident.getText());
        newAccident.setAddress(accident.getAddress());

        var optionalAccidentType = accidentTypeRepostiory.findById(accident.getType().getId());
        newAccident.setType(optionalAccidentType.get());

        Set<Rule> set = getSet(ids);
        newAccident.setRules(set);
        var optionalAccident = accidentsRepostiory.create(newAccident);
        ruleRepostiory.create(optionalAccident.get().getId(), optionalAccident.get().getRules());
        return optionalAccident;
    }

    public Optional<Accident> findById(int id) {
        return accidentsRepostiory.findById(id);
    }

    public Optional<Accident> update(Accident accident, String[] ids) {
        Set<Rule> set = getSet(ids);
        accident.setRules(set);
        var optionalAccident = accidentsRepostiory.update(accident);
        ruleRepostiory.update(accident.getId(), optionalAccident.get().getRules());
        return optionalAccident;
    }

    private Set<Rule> getSet(String[] ids) {
        Set<Rule> set = new HashSet<>();
        for (String str: ids) {
            set.add(ruleRepostiory.findById(Integer.parseInt(str)).get());
        }
        return set;
    }
}
