package com.bnp.kingmainandhiskingdom.repository;

import com.bnp.kingmainandhiskingdom.domain.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private static final List<Person> PERSONS = new CopyOnWriteArrayList<>();

    static {
        PERSONS.add(new Person("Arthur", 35));
        PERSONS.add(new Person("Lancelot", 30));
        PERSONS.add(new Person("Guinevere", 28));
        PERSONS.add(new Person("Merlin", 65));
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(PERSONS);
    }

    @Override
    public Optional<Person> findByName(String name) {
        if (name == null) {
            return Optional.empty();
        }
        return PERSONS.stream()
                .filter(p -> name.equalsIgnoreCase(p.getName()))
                .findFirst();
    }

    @Override
    public Person save(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }
        PERSONS.removeIf(p -> p.getName() != null && p.getName().equalsIgnoreCase(person.getName()));
        PERSONS.add(person);
        return person;
    }

    @Override
    public boolean deleteByName(String name) {
        if (name == null) {
            return false;
        }
        return PERSONS.removeIf(p -> name.equalsIgnoreCase(p.getName()));
    }

    @Override
    public boolean existsByName(String name) {
        if (name == null) {
            return false;
        }
        return PERSONS.stream().anyMatch(p -> name.equalsIgnoreCase(p.getName()));
    }
}
