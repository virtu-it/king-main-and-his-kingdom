package com.bnp.kingmainandhiskingdom.services;

import com.bnp.kingmainandhiskingdom.domain.Person;

import java.util.List;

public interface ArmyService {
    Person trainPerson(String name, int age);
    List<Person> getAllPersons();
    Person findPersonByName(String name);
    Person updatePerson(String name, Person person);
    boolean deletePerson(String name);
}
