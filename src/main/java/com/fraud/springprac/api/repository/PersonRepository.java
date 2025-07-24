package com.fraud.springprac.api.repository;

import com.fraud.springprac.api.dto.PersonDto;
import com.fraud.springprac.api.model.Person;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

//    @Query(value = "select jsonb_pretty(pt."attributes" -> 'height') height_jsonb from person_table pt ;)
//    List<PersonDto> findByAttribute(
//            @Param("key") String key,
//            @Param("value") String value);
}