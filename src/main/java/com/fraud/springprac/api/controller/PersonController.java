package com.fraud.springprac.api.controller;


import com.fraud.springprac.api.dto.PersonDto;
import com.fraud.springprac.api.dto.PersonResponse;
import com.fraud.springprac.api.exception.PersonNotFoundException;
import com.fraud.springprac.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// REST Controller for managing employees
@RestController
@RequestMapping("/api/")
public class PersonController {

    // GET endpoint to fetch all employees
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @PostMapping("person/byId")
    public ResponseEntity<PersonDto> getPersonByIdWithBody(@RequestBody PersonDto requestDto) throws PersonNotFoundException {
       PersonDto personDto = personService.getPersonById(requestDto.getId());
           return new ResponseEntity<>(personDto, HttpStatus.OK);
    }

    @GetMapping("person")
    public ResponseEntity<PersonResponse> getPersons(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)int pageNO,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false)int pageSize
    ) {
    return new ResponseEntity<>(personService.getAllPersons(pageNO,pageSize), HttpStatus.OK);
    }

    @GetMapping("person/{id}")
    public ResponseEntity<PersonDto> personDetail(@PathVariable int id){
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @PostMapping("person/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto personDto) {
      return new ResponseEntity<>(personService.createPerson(personDto),HttpStatus.CREATED);
    }

    @PutMapping("person/{id}/update")
    public ResponseEntity<PersonDto> updatePerson(@RequestBody PersonDto personDto, @PathVariable("id")int id){
        return ResponseEntity.ok(personService.updatePerson(personDto, id));
    }

    @DeleteMapping("person/{id}/delete")
    public ResponseEntity<String> deletePerson(@PathVariable("id") int id) {
        personService.deletePersonById(id);
        return new ResponseEntity<>("Person with id " + id + " deleted", HttpStatus.OK);
    }

}
