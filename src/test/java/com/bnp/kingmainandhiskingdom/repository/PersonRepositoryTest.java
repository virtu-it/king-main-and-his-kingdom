package com.bnp.kingmainandhiskingdom.repository;

import com.bnp.kingmainandhiskingdom.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryTest {

    private PersonRepository repository;

    @BeforeEach
    void setUp() {
        repository = new PersonRepositoryImpl();
    }

    @Test
    void testPreFilledPersonsExist() {
        List<Person> persons = repository.findAll();
        assertNotNull(persons);
        assertFalse(persons.isEmpty());
        assertTrue(repository.existsByName("Arthur"));
        assertTrue(repository.existsByName("Lancelot"));
    }

    @Test
    void testFindByName() {
        Optional<Person> arthur = repository.findByName("Arthur");
        assertTrue(arthur.isPresent());
        assertEquals("Arthur", arthur.get().getName());
        assertEquals(35, arthur.get().getAge());
    }

    @Test
    void testSavePerson() {
        Person newKnight = new Person("Bedivere", 29);
        Person saved = repository.save(newKnight);
        assertEquals("Bedivere", saved.getName());
        assertTrue(repository.existsByName("Bedivere"));
    }

    @Test
    void testDeleteByName() {
        Person temp = new Person("TemporaryKnight", 30);
        repository.save(temp);
        assertTrue(repository.existsByName("TemporaryKnight"));

        boolean deleted = repository.deleteByName("TemporaryKnight");
        assertTrue(deleted);
        assertFalse(repository.existsByName("TemporaryKnight"));
    }
}
