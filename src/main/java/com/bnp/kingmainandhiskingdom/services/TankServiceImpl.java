package com.bnp.kingmainandhiskingdom.services;

import com.bnp.kingmainandhiskingdom.domain.Person;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Tank
public class TankServiceImpl implements ArmyService {
    @Override
    public Person trainPerson(String name, int age) {
        return null;
    }

    @Override
    public List<Person> getAllPersons() {
        return Collections.emptyList();
    }

    @Override
    public Person findPersonByName(String name) {
        return null;
    }

    @Override
    public Person updatePerson(String name, Person person) {
        return null;
    }

    @Override
    public boolean deletePerson(String name) {
        return false;
    }
}
