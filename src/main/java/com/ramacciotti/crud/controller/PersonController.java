package com.ramacciotti.crud.controller;

import com.ramacciotti.crud.dto.PersonDTO;
import com.ramacciotti.crud.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin("*")
@Tag(name = "Person Controller")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary="Creates a new Person")
    public PersonDTO postPerson(@RequestBody PersonDTO personDTO) {
        log.info(">> postPerson()");
        PersonDTO result = personService.createPerson(personDTO);
        log.info("<< postPerson()");
        return result;
    }

}
