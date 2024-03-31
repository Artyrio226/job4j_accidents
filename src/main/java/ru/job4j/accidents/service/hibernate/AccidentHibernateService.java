package ru.job4j.accidents.service.hibernate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.hibernate.AccidentHibernate;
import ru.job4j.accidents.repository.hibernate.AccidentTypeHibernate;
import ru.job4j.accidents.repository.hibernate.RuleHibernate;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentHibernateService {
    private final AccidentHibernate accidentHibernate;
    private final RuleHibernate ruleHibernate;
    private final AccidentTypeHibernate accidentTypeHibernate;

    public Collection<Accident> findAll() {
        return accidentHibernate.findAll();
    }

    public Optional<Accident> create(Accident accident, String[] ids) {
        var newAccident = new Accident();
        newAccident.setName(accident.getName());
        newAccident.setText(accident.getText());
        newAccident.setAddress(accident.getAddress());

        var optionalAccidentType = accidentTypeHibernate.findById(accident.getType().getId());
        newAccident.setType(optionalAccidentType.get());
        newAccident.setRules(getSet(ids));
        return accidentHibernate.create(newAccident);
    }

    public Optional<Accident> findById(int id) {
        return accidentHibernate.findById(id);
    }

    public Optional<Accident> update(Accident accident, String[] ids) {
        accident.setRules(getSet(ids));
        return accidentHibernate.update(accident);
    }

    private Set<Rule> getSet(String[] ids) {
        Set<Rule> set = new HashSet<>();
        for (String str: ids) {
            set.add(ruleHibernate.findById(Integer.parseInt(str)).get());
        }
        return set;
    }
}
