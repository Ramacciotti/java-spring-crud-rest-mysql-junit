package com.ramacciotti.crud.service;

import com.ramacciotti.crud.dto.PersonDTO;
import com.ramacciotti.crud.model.Person;
import com.ramacciotti.crud.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.ramacciotti.crud.utils.mapper.PersonMapperDTO.convertToDTO;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonDTO createPerson(PersonDTO personDTO) {

        Person person = Person.builder()
                .cpf(personDTO.getCpf())
                .name(personDTO.getName())
                .build();

        person = personRepository.save(person);

        return convertToDTO(person);

    }

}
