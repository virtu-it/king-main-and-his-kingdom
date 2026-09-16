package com.bnp.kingmainandhiskingdom.services;

import com.bnp.kingmainandhiskingdom.domain.Person;
import com.bnp.kingmainandhiskingdom.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Army
@Service
public class ArmyServiceImpl implements ArmyService {
    @Value("${person.default-name}")
    private String defaultPersonName;

    @Value("${person.default-age}")
    private int defaultPersonAge;

    private final PersonRepository personRepository;

    public ArmyServiceImpl( PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person trainPerson(String name, int age) {
        if (age <= 20) {
            throw new IllegalArgumentException("Age must be greater than 20");
        }
        if (name == null) {
            name = this.defaultPersonName;
        }
        Person newPerson = new Person(name, age);
        return personRepository.save(newPerson);
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person findPersonByName(String name) {
        if (name == null) {
            return new Person(this.defaultPersonName,this.defaultPersonAge);
        }
        return personRepository.findByName(name).orElse(null);
    }

    @Override
    public Person updatePerson(String name, Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }
        if (person.getAge() <= 20) {
            throw new IllegalArgumentException("Age must be greater than 20");
        }
        if (name != null && !name.equalsIgnoreCase(person.getName())) {
            personRepository.deleteByName(name);
        }
        Person updated = new Person(person.getName() != null ? person.getName() : name, person.getAge());
        return personRepository.save(updated);
    }

    @Override
    public boolean deletePerson(String name) {
        if (name == null) {
            return false;
        }
        return personRepository.deleteByName(name);
    }
}
