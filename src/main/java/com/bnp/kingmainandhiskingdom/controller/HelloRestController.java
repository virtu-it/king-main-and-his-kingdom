package com.bnp.kingmainandhiskingdom.controller;

import com.bnp.kingmainandhiskingdom.domain.Person;
import com.bnp.kingmainandhiskingdom.services.Army;
import com.bnp.kingmainandhiskingdom.services.ArmyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

    private final ArmyService armyService;

    public HelloRestController(@Army ArmyService armyService) {
        this.armyService = armyService;
    }

  //TODO
}
