package com.bnp.kingmainandhiskingdom.repository;

import com.bnp.kingmainandhiskingdom.domain.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    List<Person> findAll();
    Optional<Person> findByName(String name);
    Person save(Person person);
    boolean deleteByName(String name);
    boolean existsByName(String name);
}
