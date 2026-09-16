package com.bnp.kingmainandhiskingdom.controller;

import com.bnp.kingmainandhiskingdom.domain.Person;
import com.bnp.kingmainandhiskingdom.services.Army;
import com.bnp.kingmainandhiskingdom.services.ArmyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloRestController {

    private final ArmyService armyService;

    public HelloRestController(@Army ArmyService armyService) {
        this.armyService = armyService;
    }

    @RequestMapping(path = "/welcome/{name}", method = RequestMethod.GET)
    public String hello(@PathVariable String name) {
        if(name == null){
            return "Welcome to the Kingdom, Guest!";
        }
        return "Welcome to the Kingdom, " + name + "!";
    }

    @GetMapping("/welcome-person")
    public Person welcomePerson(@RequestParam String name, @RequestParam int age) {
        if(name == null){
            return new Person("Guest", 0);
        }
        return new Person(name, age);
    }
}
