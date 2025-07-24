package com.fraud.springprac.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fraud.springprac.api.dto.PersonDto;
import com.fraud.springprac.api.dto.PersonResponse;
import com.fraud.springprac.api.exception.PersonNotFoundException;
import com.fraud.springprac.api.model.Person;
import com.fraud.springprac.api.repository.PersonRepository;
import com.fraud.springprac.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, ObjectMapper objectMapper) {
        this.personRepository = personRepository;
        this.objectMapper = objectMapper;
    }



    @Override
    public PersonDto createPerson(PersonDto personDto) {
        Person person = new Person();
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setEmail(personDto.getEmail());
        person.setAge(personDto.getAge());
        try {
            person.setAttributes(objectMapper.writeValueAsString(personDto.getAttributes()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Person newPerson = personRepository.save(person);

        return getPersonDto(newPerson);
    }

    @Override
    public PersonResponse getAllPersons(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Person> person = personRepository.findAll(pageable);
        List<Person> listOfPersons = person.getContent();
        // map because it returns a list
        List<PersonDto> content =listOfPersons.stream().map(u -> mapToPersonDto(u)).collect(Collectors.toList());

        PersonResponse personResponse = new PersonResponse();
        personResponse.setContent(content);
        personResponse.setPageNO(person.getNumber());
        personResponse.setPageSize(person.getSize());
        personResponse.setTotalElements(person.getTotalElements());
        personResponse.setTotalPages(person.getTotalPages());
        personResponse.setLast(person.isLast());

        return personResponse;
    }

    @Override
    public PersonDto getPersonById(int id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person with id " + id + " not found"));
        return mapToPersonDto(person);

    }

    @Override
    public PersonDto updatePerson(PersonDto personDto, int id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person with id " + id + " not found"));
        if (personDto.getFirstName() != null) {
            person.setFirstName(personDto.getFirstName());
        }
        if (personDto.getLastName() != null) {
            person.setLastName(personDto.getLastName());
        }
        if (personDto.getEmail() != null) {
            person.setEmail(personDto.getEmail());
        }
        if (personDto.getAge() >= 0) {
            person.setAge(personDto.getAge());
        }
        if (personDto.getAttributes() != null) {
            try{
                String attributesJson = objectMapper.writeValueAsString(personDto.getAttributes());
                person.setAttributes(attributesJson);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        personRepository.save(person);
        return personDto;
    }

    @Override
    public void deletePersonById(int id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person with id " + id + " not found"));
        personRepository.delete(person);
    }

    @Override
    public List<PersonDto> searchByAttribute(String key,String value){
        //return personRepository.findByAttribute(key, value);
        return null;
    }

    private PersonDto mapToPersonDto(Person person) {
        return getPersonDto(person);
    }

    private PersonDto getPersonDto(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setEmail(person.getEmail());
        personDto.setAge(person.getAge());
        personDto.setAttributes(person.getAttributes());
        return personDto;
    }

    private Person mapToEntity(PersonDto personDto) {
        Person person = new Person();
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setEmail(personDto.getEmail());
        person.setAge(personDto.getAge());
        return person;
    }
}
