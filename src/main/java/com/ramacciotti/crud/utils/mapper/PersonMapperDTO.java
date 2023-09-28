package com.ramacciotti.crud.utils.mapper;

import com.ramacciotti.crud.dto.PersonDTO;
import com.ramacciotti.crud.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonMapperDTO {

    public static List<PersonDTO> convertToDTO(List<Person> personList) {

        List<PersonDTO> personDTOList = new ArrayList<>();

        for (Person person : personList) {

            PersonDTO personDTO = PersonDTO.builder()
                    .name(person.getName())
                    .cpf(person.getCpf())
                    .build();

            personDTOList.add(personDTO);

        }

        return personDTOList;

    }

    public static PersonDTO convertToDTO(Person person) {

        return PersonDTO.builder()
                .name(person.getName())
                .cpf(person.getCpf())
                .build();

    }

}
