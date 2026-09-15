package com.bnp.kingmainandhiskingdom.services;

import com.bnp.kingmainandhiskingdom.domain.Person;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArmyServiceImplTest {

    @Autowired
    private ArmyService armyService;

    @Test
    void trainPerson() {
        Person person = armyService.trainPerson("Tom", 21);
        assertNotNull(person);
        assertAll(() -> {
                    assertEquals("Tom", person.getName());
                },
                () -> {
                    assertEquals(21, person.getAge());
                });

    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when age is less than 20")
    void trainPersonWithInvalidAge() {
        assertThrows(IllegalArgumentException.class, () -> {
            armyService.trainPerson("Tom", 19);
        });
    }
}