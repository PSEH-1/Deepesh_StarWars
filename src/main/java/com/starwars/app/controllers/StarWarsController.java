package com.starwars.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.starwars.app.services.IProvider;

@RestController
@RequestMapping("/api")
public class StarWarsController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("swapiService")
    private IProvider informationProvider;

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET)
    public String query(@RequestParam(value = "type") String type,
                 @RequestParam(value = "name") String name) {
        LOGGER.info("Received request to find information on: type: {}, name: {} ", type, name);
        return informationProvider.fetch(type, name);
    }

}