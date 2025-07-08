package com.fraud.springprac.api.service;

import com.fraud.springprac.api.dto.PersonDto;
import com.fraud.springprac.api.dto.PersonResponse;
import org.springframework.stereotype.Service;

@Service
public interface PersonService {
    PersonDto createPerson(PersonDto personDto);
    PersonResponse getAllPersons(int pageNo, int pageSize);
    PersonDto getPersonById(int id);
    PersonDto updatePerson(PersonDto personDto, int id);
    void deletePersonById(int id);
}
