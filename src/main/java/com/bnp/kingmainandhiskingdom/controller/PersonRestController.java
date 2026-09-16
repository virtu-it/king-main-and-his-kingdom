package com.bnp.kingmainandhiskingdom.controller;

import com.bnp.kingmainandhiskingdom.domain.Person;
import com.bnp.kingmainandhiskingdom.exception.ApiError;
import com.bnp.kingmainandhiskingdom.exception.PersonNotFoundException;
import com.bnp.kingmainandhiskingdom.services.Army;
import com.bnp.kingmainandhiskingdom.services.ArmyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/persons", "/persons"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonRestController {

    private final ArmyService armyService;

    public PersonRestController(@Army ArmyService armyService) {
        this.armyService = armyService;
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return armyService.getAllPersons();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Person> getPersonByName(@PathVariable String name) {
        Person person = armyService.findPersonByName(name);
        return ResponseEntity.ok(person);
    }

    @GetMapping("/query")
    public ResponseEntity<Person> queryPersonByName(@RequestParam String name) {
        Person person = armyService.findPersonByName(name);
        if (person == null) {
            throw new PersonNotFoundException("Person with name '" + name + "' could not be found");
        }
        return ResponseEntity.ok(person);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }
        Person trained = armyService.trainPerson(person.getName(), person.getAge());
        return new ResponseEntity<>(trained, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> updatePerson(@PathVariable String name, @RequestBody Person person) {
        Person updated = armyService.updatePerson(name, person);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deletePerson(@PathVariable String name) {
        boolean deleted = armyService.deletePerson(name);
        if (!deleted) {
            throw new PersonNotFoundException("Person with name '" + name + "' could not be found");
        }
        return ResponseEntity.noContent().build();
    }
}
