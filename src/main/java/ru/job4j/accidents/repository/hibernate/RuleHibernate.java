package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RuleHibernate {
    private final SessionFactory sf;

//    public Optional<Accident> create(Accident accident) {
//        try (Session session = sf.openSession()) {
//            session.persist(accident);
//            return Optional.of(accident);
//        }
//    }

    public List<Rule> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Rule order by id", Rule.class)
                    .list();
        }
    }

//    public Optional<Accident> update(Accident accident) {
//        try (Session session = sf.openSession()) {
//            session.merge(accident);
//            return Optional.of(accident);
//        }
//    }

    public Optional<Rule> findById(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Rule where id = :fId", Rule.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
        }
    }
}
