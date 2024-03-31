package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class AccidentHibernate {
    private final SessionFactory sf;

    public Optional<Accident> create(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Set<Rule> set = new HashSet<>();
            for (Rule rule: accident.getRules()) {
                Rule findedRule = session.find(Rule.class, rule.getId());
                set.add(findedRule);
            }
            accident.setRules(set);
            session.persist(accident);
            session.getTransaction().commit();
            return Optional.of(accident);
        }
    }

    public List<Accident> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Accident order by id", Accident.class)
                    .list();
        }
    }

    public Optional<Accident> update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(accident);
            session.getTransaction().commit();
            return Optional.of(accident);
        }
    }

    public Optional<Accident> findById(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Accident where id = :fId", Accident.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
        }
    }
}
