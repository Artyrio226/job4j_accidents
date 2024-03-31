package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeHibernate {
    private final SessionFactory sf;

//    public Optional<Accident> create(Accident accident) {
//        try (Session session = sf.openSession()) {
//            session.persist(accident);
//            return Optional.of(accident);
//        }
//    }

    public List<AccidentType> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from AccidentType order by id", AccidentType.class)
                    .list();
        }
    }

//    public Optional<Accident> update(Accident accident) {
//        try (Session session = sf.openSession()) {
//            session.merge(accident);
//            return Optional.of(accident);
//        }
//    }

    public Optional<AccidentType> findById(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery("from AccidentType where id = :fId", AccidentType.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
        }
    }
}
