package com.fraud.springprac.api.repository;

import com.fraud.springprac.api.dto.PersonDto;
import com.fraud.springprac.api.model.Person;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

//    @Query(value = """
//    SELECT * FROM person_table
//    WHERE attributes->> :key = :value
//    """,
//            countQuery = """
//    SELECT COUNT(*) FROM person_table
//    WHERE attributes->> :key = :value
//    """,
//            nativeQuery = true)
//    Page<Person> findByAttributeKeyValue(
//            @Param("key") String key,
//            @Param("value") String value,
//            Pageable pageable
//    );

    @Query(value = "SELECT * FROM egitim.person_table WHERE CAST (attributes->>?1 AS INTEGER) %s CAST(?2 as integer)", nativeQuery = true)
    Page<Person> findByAttributeKeyValue(String key, String value, Pageable pageable);

}