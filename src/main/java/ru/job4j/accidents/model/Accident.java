package ru.job4j.accidents.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accidents")
public class Accident {
    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String text;
    private String address;

    @OneToOne
    @JoinColumn(name = "types_id")
    private AccidentType type;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "accidents_rules",
            joinColumns = @JoinColumn(name = "accidents_id"),
            inverseJoinColumns = @JoinColumn(name = "rules_id"))
    private Set<Rule> rules = new HashSet<>();
}